package com.jwtAuth.smp.exception;

import com.jwtAuth.smp.dto.ErrorDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BusinessException extends RuntimeException{
    private List<ErrorDto> errors;

    public BusinessException(List<ErrorDto> errors){
        this.errors = errors;
    }

}
