package com.motorcorp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.motorcorp.exceptions.ControllerException;
import com.motorcorp.exceptions.ServiceException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "message", "data", "details"})
public class ResponseModel<T> extends CommonsBean {
    private static final long serialVersionUID = 1L;

    Map<String, String> details;

    private String message;

    private Integer code;

    private T data;

    @JsonIgnore
    private HttpStatus httpStatus;

    public ResponseModel() {
    }

    public ResponseModel(T data) {
        this.code = 200;
        this.message = "Success";
        this.data = data;
    }

    public static <E> ResponseModel<E> of(E data) {
        return new ResponseModel<>(data);
    }

    public static ResponseEntity<ResponseModel<String>> from(Throwable e) {
        ResponseModel<String> model = new ResponseModel<>();
        BeanUtils.copyProperties(e, model);
        if (e instanceof ServiceException) {
            ServiceException se = (ServiceException) e;
            model.setCode(se.getCode());
            model.setMessage(se.getMessage());
        } else if (e instanceof ControllerException) {
            ControllerException ce = (ControllerException) e;
            model.setCode(ce.getCode());
            model.setMessage(ce.getMessage());
            model.setDetails(ce.getDetails());
            return ResponseEntity.status(ce.getHttpStatus()).body(model);
        } else if (e instanceof AuthenticationException) {
            model.setHttpStatus(HttpStatus.UNAUTHORIZED);
            model.setCode(401);
            model.setMessage(e.getLocalizedMessage());
        } else {
            model.setHttpStatus(HttpStatus.BAD_REQUEST);
            model.setCode(e instanceof NoHandlerFoundException ? 404 : 500);
            model.setMessage(e.getLocalizedMessage());
        }
        return ResponseEntity.status(model.getHttpStatus()).body(model);
    }

}
