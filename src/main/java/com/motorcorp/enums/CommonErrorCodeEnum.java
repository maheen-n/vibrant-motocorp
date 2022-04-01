package com.motorcorp.enums;


import com.motorcorp.exceptions.IErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorCodeEnum implements IErrorCode {

    NOT_MODIFIED(HttpStatus.NOT_MODIFIED, 304, "Not Modified"),
    NO_CONTENT(HttpStatus.NO_CONTENT, 204, "No Content"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Not Found"),
    ALREADY_EXIST(HttpStatus.BAD_REQUEST, 405, "Already Exist"),
    ;

    private final HttpStatus httpStatus;

    private final int code;

    private final String message;

    CommonErrorCodeEnum(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
