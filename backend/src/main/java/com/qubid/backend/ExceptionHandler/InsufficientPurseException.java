package com.qubid.backend.ExceptionHandler;

public class InsufficientPurseException extends RuntimeException {
    public InsufficientPurseException(String message) {
        super(message);
    }
}
