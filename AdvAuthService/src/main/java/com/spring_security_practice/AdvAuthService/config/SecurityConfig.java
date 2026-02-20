package com.spring_security_practice.AdvAuthService.config;

import com.spring_security_practice.AdvAuthService.security.filter.JwtAuthenticationFilter;
import com.spring_security_practice.AdvAuthService.security.handler.CustomAccessDeniedHandler;
import com.spring_security_practice.AdvAuthService.security.handler.CustomAuthenticationEntryPoint;
import com.spring_security_practice.AdvAuthService.security.handler.CustomLogoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomLogoutHandler logoutHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomLogoutHandler logoutHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.logoutHandler = logoutHandler;
    }

    @Bean
    public SecurityFilterChain publicChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/auth/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                );
        return http.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(smc -> smc
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //disable csrf -> stateless session
                .csrf(AbstractHttpConfigurer::disable)
                //endpoints authorization
                .authorizeHttpRequests(auth -> auth
                        // === Admin ===
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        // === Admin & Support ===
                        .requestMatchers("/api/v1/support/**").hasAnyRole("ADMIN", "SUPPORT")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                .logout(l -> l
                        .logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext())
                        )
                );
        //basic authentication
//                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }
}
