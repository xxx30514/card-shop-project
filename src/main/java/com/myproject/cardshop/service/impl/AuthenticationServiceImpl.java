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
		  if (user.isAccountEnabled() == true) {
		        throw new IllegalStateException("帳號已經啟用，無需再次發送驗證信");
		    }
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
		 if (user.isAccountEnabled() == true) {
		        throw new IllegalStateException("帳號已經啟用，無需再次發送驗證信");
		    }
		// 查詢是否有有效的啟動碼 可能有多個有效token存在 要做處理 只取最後到期的那一個token
		Token token = tokenRepository.findByUserAndExpiredAtAfter(user, LocalDateTime.now()).stream().reduce((first, second) -> second).orElse(null);
		// 如果啟動碼有效且距離上次發送不足一定時間　限制再次發送驗證信
		if (token != null && token.getCreatedAt().plusMinutes(5).isAfter(LocalDateTime.now())) {
			throw new RuntimeException("您已經在5分鐘內發送過認證信，請稍後在試");
		}
		String newToken = generateAndSaveActivationToken(user);
		emailService.sendEmail(user.getEmail(), user.getFullName(), EmailTemplateName.ACTIVATE_ACCOUNT,
				activationUrl, newToken, "【cardshop】會員註冊通知");
	}

}
