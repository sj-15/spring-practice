package com.jwtAuth.smp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    // @Autowired
    // private CommonUtil commonUtil;

    @GetMapping("/all")
    public String allAccess(){
//        System.out.println(commonUtil.loggedInUser().getEmail());
        return "Public Content";
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String moderatorAccess(){
        return "MODERATOR/ADMIN Content";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userAccess(){
        return "USER Board";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess(){
        return "ADMIN Board";
    }
}
