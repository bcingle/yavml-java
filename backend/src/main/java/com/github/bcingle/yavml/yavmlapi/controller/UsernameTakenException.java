package com.github.bcingle.yavml.yavmlapi.controller;

public class UsernameTakenException extends Exception {

    public UsernameTakenException(String username) {
        super("username already taken: " + username);
    }
}
