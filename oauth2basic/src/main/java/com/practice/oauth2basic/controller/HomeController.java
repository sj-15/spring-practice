package com.practice.oauth2basic.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(OAuth2AuthenticationToken authentication) {
//        assert authentication.getPrincipal() != null;
        return "Hello " + authentication.getPrincipal().getAttribute("preferred_username");
    }
}
