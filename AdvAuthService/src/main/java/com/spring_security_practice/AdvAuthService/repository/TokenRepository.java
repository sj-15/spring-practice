package com.spring_security_practice.AdvAuthService.repository;

import com.spring_security_practice.AdvAuthService.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllAccessTokensByUser(Long userId);
    Optional<Token> findByAccessToken(String token);
    Optional<Token> findByRefreshToken(String token);
}
