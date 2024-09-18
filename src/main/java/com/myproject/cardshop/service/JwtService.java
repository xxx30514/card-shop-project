package com.myproject.cardshop.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

	String extractUsername(String token);
	
	String generateToken(UserDetails userDetails);
	
	String generateToken(Map<String, Object> extraClaims ,UserDetails userDetails);
	
	boolean isTokenVaild(String token , UserDetails userDetails);
}
