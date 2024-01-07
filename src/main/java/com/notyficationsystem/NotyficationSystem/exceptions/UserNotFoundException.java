package com.notyficationsystem.NotyficationSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class UserNotFoundException extends RuntimeException {

    private final HttpStatusCode errorCode = HttpStatus.NOT_FOUND;
    private static final String MESSAGE = "User not found with provided credentials";

    public UserNotFoundException() {
        super(MESSAGE);
    }

    public HttpStatusCode getErrorCode() {
        return errorCode;
    }

}