package com.qubid.backend.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceBidRequestDTO {

    @NotNull(message = "auctionPlayerId cannot be null")
    private Long auctionPlayerId;

    @NotNull(message = "franchiseId cannot be null")
    private Long franchiseId;

    @NotNull(message = "bidAmount cannot be null")
    @DecimalMin(value = "1", message = "bidAmount must be greater than 0")
    private BigDecimal bidAmount;
}
