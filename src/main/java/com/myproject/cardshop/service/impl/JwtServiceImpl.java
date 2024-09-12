package com.myproject.cardshop.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.myproject.cardshop.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {
	
	private static final String SECRET_KEY	="80BdV7dTnuhuuCHZlEkJGWVOBI4VntDg";
	
	@Override
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	@Override
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	@Override
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts
				.builder()
				.claims(extraClaims)
				.subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+ 1000 * 60 *24))
				.signWith(getSignInKey(), Jwts.SIG.HS256)
				.compact();
	}
	
	@Override
	public boolean isTokenVaild(String token, UserDetails userDetails) {
		final String userName =extractUserName(token);
		return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private <T> T extractClaim(String token,Function<Claims, T> claimsResovler) {
		final Claims claims = extractAllClaims(token);
		return claimsResovler.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts
				.parser()
		        .verifyWith(getSignInKey())
		        .build()
		        .parseSignedClaims(token)
		        .getPayload();
	}

	private SecretKey getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
