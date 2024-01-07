package com.notyficationsystem.NotyficationSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ConfirmationTokenNotSendException extends RuntimeException {
    private final HttpStatusCode errorCode = HttpStatus.CONFLICT;
    private static final String MESSAGE = "Confirmation token not sent to user with id %d";

    public ConfirmationTokenNotSendException(Integer userId) {
        super(String.format(MESSAGE, userId));
    }

    public HttpStatusCode getErrorCode() {
        return errorCode;
    }

}