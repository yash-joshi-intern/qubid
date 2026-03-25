package com.qubid.backend.services;

import com.qubid.backend.dtos.response.BidDTO;
import com.qubid.backend.dtos.response.BidGroupedDTO;

import java.util.List;

public interface BidService {

    // get all bids for one auction player — oldest first (timeline view)
    List<BidDTO> getBidHistoryByAuctionPlayer(Long auctionPlayerId);

    // get highest/winning bid for one auction player
    BidDTO getHighestBid(Long auctionPlayerId);

    // get all bids in an auction — flat list
    List<BidDTO> getAllByAuction(Long auctionId);

    // get all bids in an auction grouped by auction player
    List<BidGroupedDTO> getAllByAuctionGrouped(Long auctionId);

    // get all bids by a franchise (across all auctions)
    List<BidDTO> getAllByFranchise(Long franchiseId);

    // get all bids by a franchise in a specific auction
    List<BidDTO> getByFranchiseAndAuction(Long franchiseId, Long auctionId);

    // get all winning bids (isSold = true)
    List<BidDTO> getSoldBids();

    // get all non-winning bids (isSold = false)
    List<BidDTO> getUnsoldBids();

    // get single bid by id
    BidDTO getById(Long bidId);
}
