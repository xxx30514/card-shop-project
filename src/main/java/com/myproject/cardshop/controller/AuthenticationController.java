package com.myproject.cardshop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.myproject.cardshop.security.auth.AuthenticateRequset;
import com.myproject.cardshop.security.auth.AuthenticationResponse;
import com.myproject.cardshop.security.auth.RegisterRequset;
import com.myproject.cardshop.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterRequset request) throws MessagingException {
		authenticationService.register(request);
		return ResponseEntity.accepted().build();
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticateRequset request) {
		return ResponseEntity.ok(authenticationService.login(request));
	}

	@GetMapping("/{email}")
	public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email) {
		try {
			return ResponseEntity.ok(userDetailsService.loadUserByUsername(email));
		} catch (UsernameNotFoundException exception) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("查無此帳號" + email);
		}
	}
	
	@GetMapping("/activate-account")
	public void confirm(@RequestParam String token) throws MessagingException {
		authenticationService.activateAccount(token);
	}
	
	@PostMapping("/resend-activation-email")
	public void postMethodName(@RequestBody RegisterRequset request) throws MessagingException {
		authenticationService.resendValidationEmail(request.getEmail());
	}
	
}
