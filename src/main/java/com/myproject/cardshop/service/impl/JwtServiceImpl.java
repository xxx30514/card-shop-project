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
	
	private static final String SECRET_KEY	="sN5MEgklixS7inZPIrg8gus9X+fo7FNRK+ZhjU7nAgI=";
	
	/**
	 * 從JWT token中提取使用者名稱
	 */
	@Override
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	@Override
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	/**
	 * 產生 一個包含額外Claims的JWT token
	 */
	@Override
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts
				.builder()
				.claims(extraClaims)
				.subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+ 1000 * 60 *60 *24)) //24小時過期
				.signWith(getSignInKey(), Jwts.SIG.HS256)
				.compact();
	}
	
	/**
	 * 驗證JWT token是否過期
	 */
	@Override
	public boolean isTokenVaild(String token, UserDetails userDetails) {
		final String username = extractUsername(token);//從token中提取使用者名稱
		//userDetails.getUsername()方法比較是否為相同使用者 !isTokenExpired(token) 確認 token沒有過期
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	/**
	 * 檢查 JWT token 是否已過期
	 */
	private boolean isTokenExpired(String token) {
		//調用 extractExpiration(token) 來提取 token的過期時間 比較過期時間與當前時間
		return extractExpiration(token).before(new Date());//過期時間大於現在時間時返回false
	}
	
	/**
	 * 從JWT token中提取過期時間
	 */
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	/**
	 * 從JWT token中提取指定的Claims
	 */
	private <T> T extractClaim(String token,Function<Claims, T> claimsResovler) {
		final Claims claims = extractAllClaims(token);
		return claimsResovler.apply(claims);
	}
	
	/**
	 * 從JWT token中提取所有的Claims
	 * 舊版寫法Jwts.parser()
            .setSigningKey(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getBody();
	 */
	private Claims extractAllClaims(String token) {
		return Jwts
				.parser()
		        .verifyWith(getSignInKey())
		        .build()
		        .parseSignedClaims(token)
		        .getPayload();
	}
	
	/**
	 * 獲取用於簽名和驗證的密鑰
	 * 
	 */
	private SecretKey getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);// 將base64的密鑰解密為位元組
		return Keys.hmacShaKeyFor(keyBytes); //生成密鑰
	}

}
