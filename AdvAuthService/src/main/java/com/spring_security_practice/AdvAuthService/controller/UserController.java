package com.spring_security_practice.AdvAuthService.controller;

import com.spring_security_practice.AdvAuthService.entity.User;
import com.spring_security_practice.AdvAuthService.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/check")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> userCheck() {
        return ResponseEntity.ok("User check successful");
    }
}
