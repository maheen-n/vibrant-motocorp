package com.motorcorp.exceptions;

import com.motorcorp.controllers.CommonErrorController;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionAutoConfigure {

    @Bean
    public ErrorController errorController() {
        return new CommonErrorController();
    }
}
