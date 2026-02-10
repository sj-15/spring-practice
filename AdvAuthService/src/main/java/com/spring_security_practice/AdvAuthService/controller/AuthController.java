package com.spring_security_practice.AdvAuthService.controller;

import com.spring_security_practice.AdvAuthService.dto.LoginRequeestDTO;
import com.spring_security_practice.AdvAuthService.dto.RegisterRequestDTO;
import com.spring_security_practice.AdvAuthService.entity.User;
import com.spring_security_practice.AdvAuthService.model.AuthenticationResponse;
import com.spring_security_practice.AdvAuthService.repository.UserRepository;
import com.spring_security_practice.AdvAuthService.service.AuthenticationService;
import com.spring_security_practice.AdvAuthService.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CustomUserDetailsService authService;

    private final PasswordEncoder encoder;

    private final UserRepository userRepository;

    private final AuthenticationService authenticationService;


    public AuthController(CustomUserDetailsService authService, PasswordEncoder encoder, UserRepository userRepository, AuthenticationService authenticationService) {
        this.authService = authService;
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> createUser(@RequestBody RegisterRequestDTO user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequeestDTO user) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        return ResponseEntity.ok(userRepository.findByEmail(email));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

}
