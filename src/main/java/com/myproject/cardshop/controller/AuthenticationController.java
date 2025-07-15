package com.myproject.cardshop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.myproject.cardshop.common.response.ApiResponse;
import com.myproject.cardshop.model.User;
import com.myproject.cardshop.model.dto.UserDTO;
import com.myproject.cardshop.model.mapper.UserMapper;
import com.myproject.cardshop.security.dto.AuthenticateRequset;
import com.myproject.cardshop.security.dto.AuthenticationResponse;
import com.myproject.cardshop.security.dto.RegisterRequset;
import com.myproject.cardshop.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	private final UserDetailsService userDetailsService;
	
	private final UserMapper userMapper;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterRequset request) throws MessagingException {
		authenticationService.register(request);
		return ResponseEntity.accepted().build();
	}
	
//	@PostMapping("/register")
//	public ResponseEntity<ApiResponse<Void>> register(@RequestBody @Valid RegisterRequset request) throws MessagingException {
//		authenticationService.register(request);
//		return ResponseEntity.accepted().body(ApiResponse.success("註冊成功"));
//	}
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody @Valid AuthenticateRequset request) {
		AuthenticationResponse response = authenticationService.login(request);
		return ResponseEntity.ok(ApiResponse.success("登入成功", response));
	}

//	@GetMapping("/{email}")
//	public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email) {
//		return ResponseEntity.ok(userDetailsService.loadUserByUsername(email));
//	}
	
//	@GetMapping("/{email}")
//	public ResponseEntity<ApiResponse<UserDetails>> getUserByEmail(@PathVariable("email") String email) {
//		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//		return ResponseEntity.ok(ApiResponse.success("查詢成功", userDetails));
//	}
	@GetMapping("/{email}")
	public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email) {
	    User user = (User) userDetailsService.loadUserByUsername(email);
	    UserDTO dto = userMapper.toDto(user);
	    return ResponseEntity.ok(ApiResponse.success("查詢成功", dto));
	}
	
	@GetMapping("/activate-account")
	public void confirm(@RequestParam String token) throws MessagingException {
		authenticationService.activateAccount(token);
	}

	@PostMapping("/resend-activation-email")
	public void resendEmail(@RequestBody RegisterRequset request) throws MessagingException {
		authenticationService.resendValidationEmail(request.getEmail());
	}
	
	@PostMapping("/email")
	public ResponseEntity<?> getUserByEmail2(@RequestBody String email) {
		System.out.println("收到的 email: " + email);
	    return ResponseEntity.ok(ApiResponse.noLogin());
	}

}
