package com.spring_security_practice.AdvAuthService.service;

import com.spring_security_practice.AdvAuthService.dto.LoginRequeestDTO;
import com.spring_security_practice.AdvAuthService.dto.RegisterRequestDTO;
import com.spring_security_practice.AdvAuthService.entity.Role;
import com.spring_security_practice.AdvAuthService.entity.Token;
import com.spring_security_practice.AdvAuthService.entity.User;
import com.spring_security_practice.AdvAuthService.model.AuthenticationResponse;
import com.spring_security_practice.AdvAuthService.repository.RoleRepository;
import com.spring_security_practice.AdvAuthService.repository.TokenRepository;
import com.spring_security_practice.AdvAuthService.repository.UserRepository;
import com.spring_security_practice.AdvAuthService.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;


    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, RoleRepository roleRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
    }

    // ----- Build UserDetails --------
    private UserDetails buildUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles())
                .build();
    }

    // ----- REGISTRATION ------
    public AuthenticationResponse register(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Set.of(role));

        user = userRepository.save(user);

        UserDetails userDetails = buildUserDetails(user);

                String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        saveUserToken(accessToken, refreshToken, user);
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    // ----- LOGIN ------
    public AuthenticationResponse authenticate(LoginRequeestDTO request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        saveUserToken(accessToken, refreshToken, user);

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    // ----- LOGOUT -----


    // ----- Save User Token -----
    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    // ----- Revoke All User Token ------
    private void revokeAllUserToken(User user) {
        List<Token> tokens = tokenRepository.findAllAccessTokensByUser(user.getId());
        if (tokens.isEmpty()) return;

        tokens.forEach(t -> t.setLoggedOut(true));
        tokenRepository.saveAll(tokens);
    }

    // ----- Refresh Token -----
    public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("No user found"));

        if (jwtService.isValidRefreshToken(token, user)) {
            UserDetails userDetails = buildUserDetails(user);

            String accessToken = jwtService.generateAccessToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);

            revokeAllUserToken(user);
            saveUserToken(accessToken, refreshToken, user);
            return new ResponseEntity(new AuthenticationResponse(accessToken, refreshToken), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }


}
