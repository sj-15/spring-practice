package com.jwtAuth.generic.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponseDto {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;

    public JwtResponseDto(String token, Long id, String firstName, String lastName, List<String> roles, String email) {
        this.token = token;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
        this.email = email;
    }
}
