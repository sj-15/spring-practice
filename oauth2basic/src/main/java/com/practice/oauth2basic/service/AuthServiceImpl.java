package com.practice.oauth2basic.service;


import com.practice.oauth2basic.entity.User;

import java.util.Optional;

public class AuthServiceImpl implements AuthService {

    @Override
    public Optional<?> login(User user) {
        return Optional.empty();
    }
}
