package com.spring_security_practice.AdvAuthService.service;

import com.spring_security_practice.AdvAuthService.entity.Permission;
import com.spring_security_practice.AdvAuthService.entity.Role;
import com.spring_security_practice.AdvAuthService.entity.User;
import com.spring_security_practice.AdvAuthService.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        // ---- Convert Roles & Permissions to GrantedAuthority ----
        Set<GrantedAuthority> authorities = new HashSet<>();

        for (Role role : user.getRoles()) {

            // Add ROLE
            authorities.add(new SimpleGrantedAuthority(role.getName()));

            // Add PERMISSIONS of the role
            for (Permission permission : role.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

}
