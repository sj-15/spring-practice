package com.spring_security_practice.AdvAuthService.controller;

import com.spring_security_practice.AdvAuthService.dto.AdminUserUpdateDTO;
import com.spring_security_practice.AdvAuthService.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/check")
    public ResponseEntity<String> adminCheck() {
        return ResponseEntity.ok("Admin check successful");
    }

    @PutMapping("/updateuser/{id}")
    public ResponseEntity<Object> updateUser(@Valid @PathVariable Long id, @Valid @RequestBody AdminUserUpdateDTO dto) {
        return userService.adminUpdateUser(id, dto);
    }
}
