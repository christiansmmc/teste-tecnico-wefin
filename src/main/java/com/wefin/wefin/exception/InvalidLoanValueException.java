package com.wefin.wefin.exception;

public class InvalidLoanValueException extends RuntimeException {

    public InvalidLoanValueException(String message) {
        super(message);
    }

    public InvalidLoanValueException() {
        super("The requested loan value is invalid");
    }
}
