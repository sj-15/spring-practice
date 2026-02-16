package com.spring_security_practice.AdvAuthService.exception.auth;

import com.spring_security_practice.AdvAuthService.exception.base.BaseException;
import com.spring_security_practice.AdvAuthService.exception.base.ErrorCode;

public class TokenExpiredException extends BaseException {

    public TokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED);
    }
}
