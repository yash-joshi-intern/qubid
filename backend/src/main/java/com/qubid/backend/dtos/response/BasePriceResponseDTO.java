package com.qubid.backend.dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasePriceResponseDTO {
    private Long id;
    private Long playerId;
    private String playerFirstName;
    private String playerLastName;
    private Long tournamentId;
    private String tournamentName;
    private BigInteger basePrice;
    private Instant createdAt;
    private Instant updatedAt;
}
