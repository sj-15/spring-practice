package com.spring_security_practice.AdvAuthService.service;

import com.spring_security_practice.AdvAuthService.entity.User;
import com.spring_security_practice.AdvAuthService.model.AuthenticationResponse;
import com.spring_security_practice.AdvAuthService.repository.UserRepository;
import com.spring_security_practice.AdvAuthService.security.service.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse register(User request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(request.getRoles());

        user = userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }
}
