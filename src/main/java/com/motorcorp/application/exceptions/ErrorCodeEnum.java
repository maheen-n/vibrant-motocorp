package com.motorcorp.application.exceptions;

import com.motorcorp.exceptions.IErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCodeEnum implements IErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 10100, "User not found"),
    USER_NOT_FOUND_OR_ACTIVE(HttpStatus.BAD_REQUEST, 10110, "User not found or active"),
    USER_EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, 10120, "This email already registered"),
    USER_PHONE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, 10130, "This phone already registered"),

    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, 20100, "Customer not found"),

    VEHICLE_NOT_FOUND(HttpStatus.NOT_FOUND, 30100, "Vehicle not found"),
    VEHICLE_OWNER_NOT_MATCHING(HttpStatus.NOT_FOUND, 30350, "Vehicle owner not macthing"),

    MAINTENANCE_NOT_FOUND(HttpStatus.NOT_FOUND, 40100, "Maintenance not found"),

    ENQUIRY_NOT_FOUND(HttpStatus.NOT_FOUND, 50100, "Enquiry not found"),

    TICKET_NOT_FOUND(HttpStatus.NOT_FOUND, 60100, "Ticket not found"),
    //TICKET_STATUS_NOT_IDENTIFIED(HttpStatus.NOT_FOUND, 40650, "Ticket status not identified"),

    MODEL_NOT_FOUND(HttpStatus.NOT_FOUND, 70600, "Model not found"),
    ;

    private final HttpStatus httpStatus;

    private final int code;

    private final String message;

    ErrorCodeEnum(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}