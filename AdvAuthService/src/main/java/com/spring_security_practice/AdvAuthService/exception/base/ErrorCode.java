package com.spring_security_practice.AdvAuthService.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // === AUTH ===
    INVALID_CREDENTIALS("AUTH_001", "Invalid username or password", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED("AUTH_002", "Token has expired", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("AUTH_003", "Unauthorized access", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_INVALID("AUTH_004", "Refresh token is not valid", HttpStatus.UNAUTHORIZED),

    // === USER ===
    USER_NOT_FOUND("USR_001", "User not found", HttpStatus.NOT_FOUND),
    DUPLICATE_EMAIL("USR_002", "Email already exists", HttpStatus.CONFLICT),

    // === VALIDATION ===
    VALIDATION_FAILED("VAL_001", "Validation failed", HttpStatus.BAD_REQUEST),

    // === GENERIC ===
    INTERNAL_SERVER_ERROR("GEN_001", "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
