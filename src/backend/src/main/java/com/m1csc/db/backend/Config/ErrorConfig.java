package com.m1csc.db.backend.Config;

import org.hibernate.HibernateException;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorConfig {

    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return registry -> {
            registry.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/error"));
            registry.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/error"));
            registry.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/error"));
            registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error"));
            registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error"));
            registry.addErrorPages(new ErrorPage(HibernateException.class, "/error"));
        };
    }
}
