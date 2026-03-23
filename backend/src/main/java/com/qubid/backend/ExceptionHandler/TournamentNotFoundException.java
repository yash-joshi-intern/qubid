package com.qubid.backend.ExceptionHandler;

public class TournamentNotFoundException extends RuntimeException {
    public TournamentNotFoundException(Long id) {
        super("Tournament not found with ID: " + id);
    }
}
