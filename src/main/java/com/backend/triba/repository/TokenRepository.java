package com.backend.triba.repository;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.backend.triba.entities.Token;
import com.backend.triba.entities.User;


public interface TokenRepository extends JpaRepository<Token, UUID> {

	 @Modifying
	    @Transactional
	    @Query("DELETE FROM Token t WHERE t.user.userId = :userId")
	    int deleteTokensByUserId(@Param("userId") UUID userId);


	@Modifying
    @Transactional
    @Query("UPDATE Token t SET t.expired = true, t.revoked = true WHERE t.expirationTokenDate <= :now AND t.expired = false")
    void updateExpiredTokens(@Param("now") LocalDateTime now);
	

	Optional<Token> findByToken(String token);
	
	Optional<Token> findByRefreshToken(String refreshToken);
}
