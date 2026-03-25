package com.qubid.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewBidEventDTO {
    private Long bidId;
    private Long auctionPlayerId;
    private Long franchiseId;
    private String franchiseName;
    private BigDecimal currentHighest;
}
