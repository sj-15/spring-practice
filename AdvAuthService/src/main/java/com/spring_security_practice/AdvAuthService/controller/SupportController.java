package com.spring_security_practice.AdvAuthService.controller;

import com.spring_security_practice.AdvAuthService.dto.SupportUserUpdateDTO;
import com.spring_security_practice.AdvAuthService.entity.User;
import com.spring_security_practice.AdvAuthService.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/support")
@AllArgsConstructor
public class SupportController {
    private final UserService userService;


    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT')")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT')")
    public ResponseEntity<User> getUser(@Valid @PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/readuser")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<String> readUser() {
        return ResponseEntity.ok("Can read user");
    }

    @PutMapping("/updateuser/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT') and hasAuthority('WRITE_USER')")
    public ResponseEntity<Object> updateUser(@Valid @PathVariable Long id, @Valid @RequestBody SupportUserUpdateDTO dto) {
        return userService.supportUpdateUser(id, dto);
    }
}
