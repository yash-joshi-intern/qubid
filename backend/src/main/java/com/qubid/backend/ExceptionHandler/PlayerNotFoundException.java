package com.qubid.backend.ExceptionHandler;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(Long id) {
        super("Player not found with ID: " + id);
    }
}
