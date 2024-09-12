package com.myproject.cardshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.myproject.cardshop.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserRepository userRepository;

	@Bean
	UserDetailsService userDetailsService() {
		return userName -> userRepository.findByEmail(userName)
				.orElseThrow(() -> new UsernameNotFoundException("查無此帳號"));
	}
}
