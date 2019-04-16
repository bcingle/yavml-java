package com.github.bcingle.yavml.yavmlapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "Username already taken")
    @ExceptionHandler(UsernameTakenException.class)
    public void handleUsernameTakenException() {

    }

    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "Email address already taken")
    @ExceptionHandler(EmailTakenException.class)
    public void handleEmailTakenException() {

    }

}
