package com.spring_security_practice.AdvAuthService.exception.auth;

import com.spring_security_practice.AdvAuthService.exception.base.BaseException;
import com.spring_security_practice.AdvAuthService.exception.base.ErrorCode;

public class InvalidCredentialsException extends BaseException {

    public InvalidCredentialsException() {
        super(ErrorCode.INVALID_CREDENTIALS);
    }
}
