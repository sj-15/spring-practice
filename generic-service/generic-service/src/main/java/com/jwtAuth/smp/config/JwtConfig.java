package com.jwtAuth.smp.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "gs.app")
@Data
public class JwtConfig {
    private String jwtSecret;
    private int jwtExpirationMs;
}
 
