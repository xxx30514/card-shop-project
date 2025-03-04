package com.myproject.cardshop.service.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.myproject.cardshop.email.EmailTemplateName;
import com.myproject.cardshop.model.Role;
import com.myproject.cardshop.model.Token;
import com.myproject.cardshop.model.User;
import com.myproject.cardshop.repository.RoleRepository;
import com.myproject.cardshop.repository.TokenRepository;
import com.myproject.cardshop.repository.UserRepository;
import com.myproject.cardshop.security.auth.AuthenticateRequset;
import com.myproject.cardshop.security.auth.AuthenticationResponse;
import com.myproject.cardshop.security.auth.RegisterRequset;
import com.myproject.cardshop.service.AuthenticationService;
import com.myproject.cardshop.service.EmailService;
import com.myproject.cardshop.service.JwtService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final TokenRepository tokenRepository;

	private final EmailService emailService;

	private final JwtService jwtService;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	@Value("${application.mailing.fronted.activation-url}")
	private String activationUrl;

	@Override
	public AuthenticationResponse login(AuthenticateRequset request) {
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		HashMap<String, Object> claims = new HashMap<String, Object>();
		User user = ((User) auth.getPrincipal()); // 直接從 Authentication 取得 User 物件
		claims.put("fullName", user.getFullName());
		String jwtToken = jwtService.generateToken(claims, user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	@Override
	public void register(RegisterRequset request) throws MessagingException {
		Role userRole = roleRepository.findByName("USER")
				.orElseThrow(() -> new IllegalStateException("ROLE USER was not initialized"));
		User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
				.email(request.getEmail()).userPassword(passwordEncoder.encode(request.getPassword()))
				.accountLocked(false).accountEnabled(false).roles(List.of(userRole)).build();
		userRepository.save(user);
		sendValidationEmail(user);
	}

	// 寄出帳號驗證信
	private void sendValidationEmail(User user) throws MessagingException {
		String newToken = generateAndSaveActivationToken(user);
		emailService.sendEmail(user.getEmail(), user.getFullName(), EmailTemplateName.ACTIVATE_ACCOUNT, activationUrl,
				newToken, "【cardshop】會員註冊通知");

	}

	// 產生並儲存帳號啟動碼
	private String generateAndSaveActivationToken(User user) {
		String generatedToken = generateAndSaveActivationCode(6);
		Token token = Token.builder().token(generatedToken).createdAt(LocalDateTime.now())
				.expiredAt(LocalDateTime.now().plusMinutes(15)).user(user).build();
		tokenRepository.save(token);
		return generatedToken;
	}

	// 產生帳號啟動碼
	private String generateAndSaveActivationCode(int length) {
		String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder codeBuilder = new StringBuilder();
		SecureRandom secureRandom = new SecureRandom();
		for (int i = 0; i < length; i++) {
			int randomIndex = secureRandom.nextInt(characters.length());
			codeBuilder.append(characters.charAt(randomIndex));
		}
		return codeBuilder.toString();
	}

	@Override
	@Transactional
	public void activateAccount(String token) throws MessagingException {
		// 確認啟動碼是否存在
		Token savedToken = tokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("無效的Token"));
		// 確認啟動碼是否過期
		if (LocalDateTime.now().isAfter(savedToken.getExpiredAt())) {
			// sendValidationEmail(savedToken.getUser()); //應該另外請求新的啟動碼
			throw new RuntimeException("帳號啟動碼已過期，請重新進行認證");
		}
		// 確認使用者是否存在
		User user = userRepository.findById(savedToken.getUser().getId())
				.orElseThrow(() -> new UsernameNotFoundException("查無使用者"));
		// 啟動帳號
		user.setAccountEnabled(true);
		userRepository.save(user);
		// 紀錄驗證時間
		savedToken.setValidatedAt(LocalDateTime.now());
		tokenRepository.save(savedToken);
	}

	// 重新寄出認證信
	@Override
	public void resendValidationEmail(String email) throws MessagingException {
		// 根據 email 查詢 User
		User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("查無使用者"));
		// 查詢是否有有效的啟動碼
		Token token = tokenRepository.findByUserAndExpiredAtAfter(user, LocalDateTime.now()).orElse(null);
		// 如果找不到有效的 token (過期或沒有)，則重新生成一個新的token 並發送認證信
		if (token == null) {
			String newToken = generateAndSaveActivationToken(user);
			emailService.sendEmail(user.getEmail(), user.getFullName(), EmailTemplateName.ACTIVATE_ACCOUNT,
					activationUrl, newToken, "【cardshop】會員註冊通知");
		}
	}

}
