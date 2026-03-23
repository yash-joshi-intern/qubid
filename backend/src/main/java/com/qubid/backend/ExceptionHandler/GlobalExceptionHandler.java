package com.qubid.backend.ExceptionHandler;

import com.qubid.backend.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        System.out.println(e.getBindingResult());
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String msg = error.getDefaultMessage();
            errorMap.put(fieldName, msg);
        });
        return errorMap;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Some Error Occurred", e.getMessage()));
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handlePlayerNotFoundException(PlayerNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Player Not Found", e.getMessage()));
    }

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleTeamNotFoundException(TeamNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Team Not Found", e.getMessage()));
    }

    @ExceptionHandler(TournamentNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleTournamentNotFoundException(TournamentNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Tournament Not Found", e.getMessage()));
    }
}
