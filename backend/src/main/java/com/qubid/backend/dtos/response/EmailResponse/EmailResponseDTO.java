package com.qubid.backend.dtos.response.EmailResponse;

import com.qubid.backend.enums.EmailStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailResponseDTO {
    private EmailStatus status;
    private String message;
    private LocalDateTime sentAt;
}