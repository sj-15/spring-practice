package com.jwtAuth.smp.exception;

import com.jwtAuth.smp.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorDto>> handelFieldValidation(MethodArgumentNotValidException manv) {
        List<ErrorDto> errorDtoList = new ArrayList<>();
        ErrorDto errorDto = null;

        List<FieldError> fieldErrorList = manv.getBindingResult().getFieldErrors();

        for (FieldError fe : fieldErrorList) {
            logger.debug("Inside field validation: {} - {}", fe.getField(), fe.getDefaultMessage());
            logger.info("Inside field validation: {} - {}", fe.getField(), fe.getDefaultMessage());
            errorDto = new ErrorDto();
            errorDto.setCode(fe.getField());
            errorDto.setMessage(fe.getDefaultMessage());
            errorDtoList.add(errorDto);
        }

        return new ResponseEntity<>(errorDtoList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<List<ErrorDto>> handelBusinessException(BusinessException bex) {
        for (ErrorDto em : bex.getErrors()) {
            logger.debug("BusinessException is thrown - level - debug: {} - {}", em.getCode(), em.getMessage());
            logger.info("BusinessException is thrown - level - info: {} - {}", em.getCode(), em.getMessage());
            logger.warn("BusinessException is thrown - level - warn: {} - {}", em.getCode(), em.getMessage());
            logger.error("BusinessException is thrown - level - error: {} - {}", em.getCode(), em.getMessage());
        }

        return new ResponseEntity<>(bex.getErrors(), HttpStatus.BAD_REQUEST);
    }
}
