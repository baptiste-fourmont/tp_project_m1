package com.m1csc.db.backend.Config;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", "Une erreur s'est produite : " + e.getMessage());
        return "error";
    }
}
