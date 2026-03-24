package com.qubid.backend.dtos.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionPlayerRequestDTO {


    @NotNull(message = "playerId is required")
    private Long playerId;

    @NotNull(message = "basePrice is required")
    @Min(value = 1, message = "basePrice must be greater than 0")
    private BigInteger basePrice;

    @NotNull(message = "auctionId is required")
    private Long auctionId;

}
