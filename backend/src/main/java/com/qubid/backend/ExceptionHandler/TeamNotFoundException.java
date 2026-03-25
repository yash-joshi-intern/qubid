package com.qubid.backend.ExceptionHandler;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(Long id) {
        super("Team not found with ID: " + id);
    }
}