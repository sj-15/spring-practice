package com.spring_security_practice.AdvAuthService.security.service;

import com.spring_security_practice.AdvAuthService.entity.User;
import com.spring_security_practice.AdvAuthService.exception.auth.RefreshTokenInvalidException;
import com.spring_security_practice.AdvAuthService.exception.common.ValidationException;
import com.spring_security_practice.AdvAuthService.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${spring.security.secret}")
    private String SECRET_KEY;

    @Value("${spring.security.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${spring.security.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    private SecretKey signingKey;
    private final TokenRepository tokenRepository;

    public JwtService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, accessTokenExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails, refreshTokenExpiration);
    }

    public String generateToken(UserDetails userDetails, Long tokenExpiration) {
        Collection<String> roles = extractRoles(userDetails);
        Set<String> permissions = extractPermissions(userDetails);
        Map<String, Object> claims = Map.of("roles", roles,
                "permissions", permissions);

        Instant now = Instant.now();
        String token = Jwts
                .builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(tokenExpiration)))
                .signWith(signingKey)
                .compact();
        return token;
    }

    private Collection<String> extractRoles(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    // ---- Extract Permissions ----
    private Set<String> extractPermissions(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).filter(Objects::nonNull)
                .filter(authority -> !authority.startsWith("ROLE_")) // keep only permissions
                .collect(Collectors.toSet());
    }


    // ------- VALIDATION --------
    public boolean isValid(String token, UserDetails user) {
        try {
            String username = extractUsername(token);

            boolean validToken = tokenRepository
                    .findByAccessToken(token)
                    .map(t -> !t.isRevoked())
                    .orElse(false);

            return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
        } catch (JwtException | IllegalArgumentException | ValidationException ex) {
            throw new ValidationException();
        }
    }

    public boolean isValidRefreshToken(String token, User user) {
        try {
            String username = extractUsername(token);

            boolean validRefreshToken = tokenRepository
                    .findByRefreshToken(token)
                    .map(t -> !t.isRevoked())
                    .orElse(false);

            return (username.equals(user.getEmail())) && !isTokenExpired(token) && validRefreshToken;
        } catch (JwtException | IllegalArgumentException | RefreshTokenInvalidException ex) {
            throw new RefreshTokenInvalidException();
        }
    }

    // ------- CLAIMS --------
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
