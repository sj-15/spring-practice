package com.jwtAuth.smp.controller;

import com.jwtAuth.smp.constant.ERole;
import com.jwtAuth.smp.dto.*;
import com.jwtAuth.smp.entity.RoleEntity;
import com.jwtAuth.smp.entity.UserEntity;
import com.jwtAuth.smp.exception.BusinessException;
import com.jwtAuth.smp.repository.RoleRepository;
import com.jwtAuth.smp.repository.UserRepository;
import com.jwtAuth.smp.service.JwtServiceImpl;
import com.jwtAuth.smp.service.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/noauth")
public class AuthContoller {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtServiceImpl jwtService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponseDto(jwt, userDetails.getId(), userDetails.getFirstName(), userDetails.getLastName(), roles, userDetails.getEmail()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        if (userRepository.existsByEmail(signupRequestDto.getEmail())) {
//            return ResponseEntity.badRequest().body(new MessageResponseDto("Error: Email is already taken!"));
            ErrorDto err = new ErrorDto();
            err.setCode("AUTH_OO5");
            err.setMessage("Email is already taken!");
            throw new BusinessException(List.of(err));
        }

        UserEntity user = new UserEntity();
        user.setFirstName(signupRequestDto.getFirstName());
        user.setLastName(signupRequestDto.getLastName());
        user.setEmail(signupRequestDto.getEmail());
        user.setPassword(encoder.encode(signupRequestDto.getPassword()));
        Set<String> strRoles = signupRequestDto.getRoles();
        Set<RoleEntity> roles = new HashSet<>();
        if (strRoles == null) {
            RoleEntity userRole = roleRepository.findByName(ERole.ROLE_CONSUMER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "ROLE_CONSUMER":
                        RoleEntity userRole = roleRepository.findByName(ERole.ROLE_CONSUMER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                        break;
                    case "ROLE_PROVIDER":
                        RoleEntity modRole = roleRepository.findByName(ERole.ROLE_PROVIDER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        RoleEntity defRole = roleRepository.findByName(ERole.ROLE_CONSUMER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(defRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return new ResponseEntity<>(new MessageResponseDto("User registered successfully!"), HttpStatus.CREATED);
    }
}
