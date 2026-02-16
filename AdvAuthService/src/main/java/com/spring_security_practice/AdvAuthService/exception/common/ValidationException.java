package com.spring_security_practice.AdvAuthService.exception.common;

import com.spring_security_practice.AdvAuthService.exception.base.BaseException;
import com.spring_security_practice.AdvAuthService.exception.base.ErrorCode;

public class ValidationException extends BaseException {
    public ValidationException(){
        super(ErrorCode.VALIDATION_FAILED);
    }
}
