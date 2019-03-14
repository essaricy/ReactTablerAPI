package com.softvision.digital.web.config.advice;

import com.softvision.digital.common.model.ResultDto;
import com.softvision.digital.common.util.ResultUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

//@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = { ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultDto constraintViolationException(ConstraintViolationException ex, WebRequest req) {
        String message = ex.getConstraintViolations().iterator().next().getMessage();
        return ResultUtil.getFailure(message);
    }

    @ExceptionHandler(value = { ValidationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultDto validationException(ValidationException ex, WebRequest req) {
        return ResultUtil.getFailure(ex.getMessage());
    }

    @ExceptionHandler(value = { AuthenticationException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultDto authenticationException(AuthenticationException ex, WebRequest req) {
        return ResultUtil.getFailure(ex.getMessage());
    }

}