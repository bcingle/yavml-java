package com.github.bcingle.yavml.yavmlapi.controller;

public class EmailTakenException extends Exception {

    public EmailTakenException(String email) {
        super("Email address is already taken: " + email);
    }
}
