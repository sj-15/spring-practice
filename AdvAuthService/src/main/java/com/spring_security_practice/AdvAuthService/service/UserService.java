package com.spring_security_practice.AdvAuthService.service;

import com.spring_security_practice.AdvAuthService.dto.AdminUserUpdateDTO;
import com.spring_security_practice.AdvAuthService.dto.SupportUserUpdateDTO;
import com.spring_security_practice.AdvAuthService.dto.UserSelfUpdateDTO;
import com.spring_security_practice.AdvAuthService.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    // USER
    public ResponseEntity<User> getUserByEmail(String email);
    public ResponseEntity<List<User>> getAllUsers();

    // UPDATE
    public ResponseEntity<Object> selfUpdateUser(UserSelfUpdateDTO userSelfUpdateDTO);
    public ResponseEntity<Object> supportUpdateUser(Long id, SupportUserUpdateDTO userSelfUpdateDTO);
    public ResponseEntity<Object> adminUpdateUser(Long id, AdminUserUpdateDTO adminUserUpdateDTO);
}
