package com.wefin.wefin.exception;

public class InvalidInstallmentNumberException extends RuntimeException {

    public InvalidInstallmentNumberException(String message) {
        super(message);
    }

    public InvalidInstallmentNumberException() {
        super("The requested installment number is invalid");
    }
}
