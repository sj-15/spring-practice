package com.spring_security_practice.AdvAuthService.controller;

import com.spring_security_practice.AdvAuthService.entity.User;
import com.spring_security_practice.AdvAuthService.exception.user.UserNotFoundException;
import com.spring_security_practice.AdvAuthService.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/support")
public class SupportController {
    private final UserRepository userRepository;

    public SupportController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT')")
    public ResponseEntity<User> getUser(@Valid @PathVariable String email) {
        return ResponseEntity.ok(userRepository
                .findByEmail(email).orElseThrow(UserNotFoundException::new));
    }

    @GetMapping("/readuser")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<String> readUser(){
        return ResponseEntity.ok("Can read user");
    }
}
