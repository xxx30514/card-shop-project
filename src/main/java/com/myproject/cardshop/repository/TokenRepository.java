package com.myproject.cardshop.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myproject.cardshop.model.Token;
import com.myproject.cardshop.model.User;

public interface TokenRepository extends JpaRepository<Token, Integer> {
	
	Optional<Token> findByToken(String token);

	Optional<Token> findByUserAndExpiredAtAfter(User user, LocalDateTime now);
	
	@Query("SELECT t FROM Token t WHERE t.user = :user AND t.expiredAt > :now ORDER BY t.expiredAt ASC")
	Optional<Token> findFirstByUserAndExpiredAtAfterOrderByExpiredAtAsc(@Param("user") User user, @Param("now") LocalDateTime now);
}
