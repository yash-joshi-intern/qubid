package com.qubid.backend.repository;

import com.qubid.backend.entities.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    Optional<Bid> findTopByAuctionPlayerIdOrderByCurrentPriceDesc(Long actionPlayerId);

    List<Bid> findAllByAuctionPlayerId(Long auctionPlayerId);

    List<Bid> findAllByFranchiseId(Long franchiseId);

    List<Bid> findAllByAuctionPlayer_AuctionId(Long auctionPlayerAuctionId);

    List<Bid> findAllByIsSold(boolean isSold);

    List<Bid> findAllByFranchiseIdAndAuctionPlayer_Auction_Id(Long franchiseId, Long auctionId);
    
}
