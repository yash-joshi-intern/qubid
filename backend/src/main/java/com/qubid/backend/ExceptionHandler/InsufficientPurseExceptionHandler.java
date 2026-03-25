package com.qubid.backend.ExceptionHandler;

import com.qubid.backend.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InsufficientPurseExceptionHandler {
    @ExceptionHandler(InsufficientPurseException.class)
    public ResponseEntity<ApiResponse<Object>> InsufficientPurseExceptionHandler(InsufficientPurseException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Not enough balance", e.getMessage()));
    }
}
