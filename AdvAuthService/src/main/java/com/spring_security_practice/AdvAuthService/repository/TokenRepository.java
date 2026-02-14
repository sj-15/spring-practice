package com.spring_security_practice.AdvAuthService.repository;

import com.spring_security_practice.AdvAuthService.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Query;
=======
>>>>>>> 64c7e89bba940cc3ce550a71aa125823eb7daa65
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
<<<<<<< HEAD
    @Query("""
            select t from Token t inner join User u on t.user.id = u.id
            where t.user.id = :userId and t.isLoggedOut = false
            """)
    List<Token> findAllAccessTokensByUser(Long userId);

    Optional<Token> findByAccessToken(String token);

=======
    List<Token> findAllAccessTokensByUser(Long userId);
    Optional<Token> findByAccessToken(String token);
>>>>>>> 64c7e89bba940cc3ce550a71aa125823eb7daa65
    Optional<Token> findByRefreshToken(String token);
}
