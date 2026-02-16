package com.spring_security_practice.AdvAuthService.exception.user;

import com.spring_security_practice.AdvAuthService.exception.base.BaseException;
import com.spring_security_practice.AdvAuthService.exception.base.ErrorCode;

public class DuplicateEmailException extends BaseException {
    public DuplicateEmailException(){
        super(ErrorCode.DUPLICATE_EMAIL);
    }
}
