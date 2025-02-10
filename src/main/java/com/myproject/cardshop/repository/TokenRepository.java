package com.myproject.cardshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.cardshop.model.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {
	
	Optional<Token> findByToken(String token);
}
