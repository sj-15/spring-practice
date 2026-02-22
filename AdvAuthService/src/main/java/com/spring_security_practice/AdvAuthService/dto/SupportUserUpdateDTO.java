package com.spring_security_practice.AdvAuthService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupportUserUpdateDTO {
    @NotBlank(message = "New email is required")
    @Email(message = "New email is invalid")
    private String email;
}
