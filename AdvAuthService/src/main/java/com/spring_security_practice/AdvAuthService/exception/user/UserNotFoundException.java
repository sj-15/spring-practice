package com.spring_security_practice.AdvAuthService.exception.user;

import com.spring_security_practice.AdvAuthService.exception.base.BaseException;
import com.spring_security_practice.AdvAuthService.exception.base.ErrorCode;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(){
        super(ErrorCode.USER_NOT_FOUND);
    }
}
