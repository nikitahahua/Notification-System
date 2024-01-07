package com.notyficationsystem.NotyficationSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class InvalidConfirmationTokenException extends RuntimeException {

    private final HttpStatusCode errorCode = HttpStatus.NOT_FOUND;
    private static final String MESSAGE = "Invalid confirmation token";

    public InvalidConfirmationTokenException() {
        super(MESSAGE);
    }

    public HttpStatusCode getErrorCode() {
        return errorCode;
    }

}