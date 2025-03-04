package com.myproject.cardshop.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.myproject.cardshop.model.Token;
import com.myproject.cardshop.model.User;

public interface TokenRepository extends JpaRepository<Token, Integer> {
	
	Optional<Token> findByToken(String token);

	Optional<Token> findByUserAndExpiredAtAfter(User user, LocalDateTime now);
}
