package com.qubid.backend.dtos.Response;

import com.qubid.backend.enums.AuctionPlayerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidGroupedDTO {
    private Long auctionPlayerId;
    private String playerName;
    private AuctionPlayerStatus playerStatus;
    private BigDecimal highestBid;
    private String highestBidFranchise;
    private int totalBids;
    private List<BidDTO> bids;
}
