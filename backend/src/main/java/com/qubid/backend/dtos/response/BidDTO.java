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
public class BidDTO {
    private Long bidId;
    private Long auctionPlayerId;
    private String playerName;
    private Long franchiseId;
    private String franchiseName;
    private BigDecimal bidAmount;
    private boolean isSold;
}
