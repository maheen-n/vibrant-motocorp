package com.motorcorp.exceptions;

import com.motorcorp.models.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice(basePackages = "com.motorcorp")
public class ExceptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ResponseModel<String>> serviceException(ServiceException e) {
        return ResponseModel.from(e);
    }

    @ResponseBody
    @ExceptionHandler(ControllerException.class)
    public ResponseEntity<ResponseModel<String>> exception(Exception e) {
        return ResponseModel.from(e);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ResponseModel<String>> handleAuthenticationException(AuthenticationException e) {
        return ResponseModel.from(e);
    }
}
