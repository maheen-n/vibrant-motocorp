package com.motorcorp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthenticated")
public class AuthenticationException extends org.springframework.security.core.AuthenticationException {

    public AuthenticationException(String msg) {
        super(msg);
    }
}
