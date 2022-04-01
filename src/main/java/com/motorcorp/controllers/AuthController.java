package com.motorcorp.controllers;

import com.motorcorp.exceptions.AuthenticationException;
import com.motorcorp.exceptions.ControllerException;
import com.motorcorp.models.AuthToken;
import com.motorcorp.models.LoginRequest;
import com.motorcorp.models.ResponseModel;
import com.motorcorp.models.UserSession;
import com.motorcorp.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "login")
    public ResponseModel<AuthToken> login(@RequestBody @Valid LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        AuthToken authToken = authService.login(loginRequest.getUserName(), loginRequest.getPassword());
        return ResponseModel.of(authToken);
    }

    @GetMapping("validate")
    public ResponseModel<AuthToken> validateToken(@RequestParam("token") String token) {
        if (StringUtils.isBlank(token))
            throw new AuthenticationException("token cannot be blank or empty");
        AuthToken authToken = authService.validateToken(token);
        return ResponseModel.of(authToken);
    }

    @GetMapping("logout")
    public ResponseModel<AuthToken> validateToken(@AuthenticationPrincipal UserSession userSession) {
        if (userSession ==null || StringUtils.isBlank(userSession.getToken()))
            throw new AuthenticationException("token cannot be blank or empty");
        AuthToken authToken = authService.logout(userSession);
        return ResponseModel.of(authToken);
    }
}
