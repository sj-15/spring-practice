package com.spring_security_practice.AdvAuthService.controller;

import com.spring_security_practice.AdvAuthService.dto.UserSelfUpdateDTO;
import com.spring_security_practice.AdvAuthService.repository.UserRepository;
import com.spring_security_practice.AdvAuthService.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/check")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> userCheck() {
        return ResponseEntity.ok("User check successful");
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/me")
    public ResponseEntity<Object> updatePassword(@RequestBody UserSelfUpdateDTO userSelfUpdateDTO) {
        return userService.selfUpdateUser(userSelfUpdateDTO);
    }
}
