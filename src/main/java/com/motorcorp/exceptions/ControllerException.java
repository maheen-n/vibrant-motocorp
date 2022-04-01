package com.motorcorp.exceptions;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerException extends RuntimeException {

    @Getter
    private final HttpStatus httpStatus;

    @Getter
    private final int code;

    @Getter
    private final Map<String, String> details;

    @Getter
    private final String message;


    public ControllerException(BindingResult bindingResult) {
        httpStatus = HttpStatus.BAD_REQUEST;
        this.code = this.httpStatus.value();
        message = this.httpStatus.getReasonPhrase();
        details = new HashMap<>();
        List<FieldError> errors = bindingResult.getFieldErrors();

        for (FieldError e : errors) {
            details.put(e.getField(), e.getDefaultMessage());
        }
        if (bindingResult.hasGlobalErrors()) {
            for (ObjectError e : bindingResult.getGlobalErrors()) {
                details.put(e.getObjectName(), e.getDefaultMessage());
            }
        }
    }

    public ControllerException(IErrorCode iError) {
        super(iError.getMessage());
        this.httpStatus = iError.getHttpStatus();
        this.code = iError.getCode();
        this.message = iError.getMessage();
        this.details = new HashMap<>();
    }
    public ControllerException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.code = this.httpStatus.value();
        this.message = message;
        details = new HashMap<>();
    }
}
