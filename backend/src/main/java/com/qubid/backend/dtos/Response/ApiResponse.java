package com.qubid.backend.dtos.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponse<T> {
    private T data;
    private String message;
    private boolean isError;
    private String errorMessage;

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .data(data)
                .message(message)
                .isError(false)
                .build();
    }

    public static <T> ApiResponse<T> error(String message, String error) {
        return ApiResponse.<T>builder()
                .message(message)
                .isError(true)
                .errorMessage(error)
                .build();
    }
}
