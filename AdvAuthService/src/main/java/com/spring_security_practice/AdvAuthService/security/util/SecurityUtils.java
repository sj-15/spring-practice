package com.spring_security_practice.AdvAuthService.security.util;

import com.spring_security_practice.AdvAuthService.model.CustomUserDetails;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor
public class SecurityUtils {
    public static CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new RuntimeException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails)) {
            throw new IllegalStateException("Invalid authentication principal");
        }

        return (CustomUserDetails) principal;

    }

    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public static String getCurrentUserName() {
        return getCurrentUser().getUsername();
    }

    public static boolean hasAuthority(String authority) {
        return getCurrentUser().getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(authority));
    }
}
