package com.motorcorp.exceptions;


import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

    @Getter
    private final HttpStatus httpStatus;

    @Getter
    private final int code;

    @Getter
    private final String message;

    public ServiceException(IErrorCode iError) {
        super(iError.getMessage());
        this.httpStatus = iError.getHttpStatus();
        this.code = iError.getCode();
        this.message = iError.getMessage();
    }

    public ServiceException(IErrorCode iError, String message) {
        super(message);
        this.httpStatus = iError.getHttpStatus();
        this.code = iError.getCode();
        this.message = message;
    }
}
