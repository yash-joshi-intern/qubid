package com.qubid.backend.dtos.response;

import com.qubid.backend.enums.AuctionPlayerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuctionPlayerDTO {
    private Long auctionPlayerId;
    private Long playerId;
    private String firstName;
    private String lastName;
    private String country;
    private AuctionPlayerStatus status;
    private BigInteger basePrice;
    private Long auctionId;
    private String auctionTitle;
    private Long soldToFranchiseId;
    private String soldToFranchiseName;
    private BigDecimal finalSoldPrice;
}
