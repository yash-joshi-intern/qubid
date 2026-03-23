package com.qubid.backend.dtos.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasePriceRequestDTO {

    @NotNull(message = "playerId cannot be null")
    private Long playerId;

    @NotNull(message = "tournamentId cannot be null")
    private Long tournamentId;

    @NotNull(message = "basePrice cannot be null")
    @Positive(message = "basePrice must be a positive value")
    private BigDecimal basePrice;
}
