package com.exception;

public class EmailInUseException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE = "Email is already associated with another user";

    public EmailInUseException(String errorMessage) {
        super(errorMessage);
    }

    public EmailInUseException() {
        super(DEFAULT_MESSAGE);
    }
}
