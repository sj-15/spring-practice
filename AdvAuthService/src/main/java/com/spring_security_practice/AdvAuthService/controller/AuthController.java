package com.spring_security_practice.AdvAuthService.controller;

import com.spring_security_practice.AdvAuthService.dto.LoginRequeestDTO;
import com.spring_security_practice.AdvAuthService.dto.RegisterRequestDTO;
import com.spring_security_practice.AdvAuthService.model.AuthenticationResponse;
import com.spring_security_practice.AdvAuthService.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
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


    @PostMapping("/refresh_token")
    public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response){
        return authenticationService.refreshToken(request, response);
    }
}
