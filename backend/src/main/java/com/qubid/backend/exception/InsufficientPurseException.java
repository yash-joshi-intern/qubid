package com.qubid.backend.exception;

public class InsufficientPurseException extends RuntimeException {
    public InsufficientPurseException(String message) {
        super(message);
    }
}
