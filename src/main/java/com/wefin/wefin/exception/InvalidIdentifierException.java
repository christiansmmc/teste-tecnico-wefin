package com.wefin.wefin.exception;

public class InvalidIdentifierException extends RuntimeException {

    public InvalidIdentifierException(String message) {
        super(message);
    }

    public InvalidIdentifierException() {
        super("The identifier is invalid");
    }
}
