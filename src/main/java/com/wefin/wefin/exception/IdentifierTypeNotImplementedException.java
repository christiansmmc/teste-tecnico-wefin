package com.wefin.wefin.exception;

public class IdentifierTypeNotImplementedException extends RuntimeException {

    public IdentifierTypeNotImplementedException(String message) {
        super(message);
    }

    public IdentifierTypeNotImplementedException() {
        super("Identifier Type not implemented");
    }
}
