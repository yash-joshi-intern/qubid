package com.qubid.backend.dtos.response.EmailResponse;


import lombok.Data;

@Data
public class PlayerSoldEmailResponseWrapper {
    private EmailResponseDTO data;
    private String message;
    private boolean success;
}