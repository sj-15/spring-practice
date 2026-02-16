package com.spring_security_practice.AdvAuthService.exception.common;

import com.spring_security_practice.AdvAuthService.exception.base.BaseException;
import com.spring_security_practice.AdvAuthService.exception.base.ErrorCode;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(){
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
