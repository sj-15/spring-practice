package com.practice.oauth2basic.service;

import com.practice.oauth2basic.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AuthService {
    public Optional<?> login(User user);
}
