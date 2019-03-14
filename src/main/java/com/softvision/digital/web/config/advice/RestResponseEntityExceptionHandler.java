package com.softvision.digital.web.config.advice;

import com.softvision.digital.common.model.ResultDto;
import com.softvision.digital.common.util.ResultUtil;
import org.apache.commons.lang.IncompleteArgumentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;

//@ControllerAdvice
//@RestController
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResultDto resultDto = ResultUtil.getFailure(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity(resultDto, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<ResultDto> onIncompleteArgumentException(IncompleteArgumentException ex) {
        return new ResponseEntity(ResultUtil.getFailure(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<ResultDto> onFileNotFoundException(FileNotFoundException ex) {
        return new ResponseEntity(ResultUtil.getFailure(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    protected ResponseEntity<ResultDto> onRuntimeException(RuntimeException ex) {
        return new ResponseEntity(ResultUtil.getFailure(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity<ResultDto> onException(Exception ex) {
        return new ResponseEntity(ResultUtil.getFailure(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
