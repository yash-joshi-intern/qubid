package com.qubid.backend.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePriceRequestDTO {
    @NotNull(message = "Player ID is required")
    private Long playerId;

    @NotNull(message = "Tournament ID is required")
    private Long tournamentId;

    @NotNull(message = "Base price is required")
    @Positive(message = "Base price must be positive")
    private BigInteger basePrice;
}
