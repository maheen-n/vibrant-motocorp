package com.motorcorp.controllers;

import com.motorcorp.enums.CommonErrorCodeEnum;
import com.motorcorp.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class CommonErrorController implements ErrorController {
    @Value("${server.error.path:${error.path:/error}}")
    private String errorPath;

  /*  @RequestMapping(value = "/error", method = {GET, POST, PUT, DELETE})
    public void errorController(HttpServletRequest aRequest) {
        throw new ServiceException(CommonErrorCodeEnum.NOT_FOUND);
    }*/

}
