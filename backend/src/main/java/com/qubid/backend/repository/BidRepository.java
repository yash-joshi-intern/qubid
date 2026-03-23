package com.qubid.backend.repository;

import com.qubid.backend.entities.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
//    List<Bid> findByAuctionPlayerIdOrderByCurrentPriceDesc(Long actionPlayerId);

    Optional<Bid> findTopByAuctionPlayerIdOrderByCurrentPriceDesc(Long actionPlayerId);

    List<Bid> findAllByAuctionPlayerId(Long auctionPlayerId);

    List<Bid> findAllByFranchiseId(Long franchiseId);

    @Query("SELECT b FROM Bid b WHERE b.auctionPlayer.auction.id = :auctionId")
    List<Bid> findAllByAuctionId(@Param("auctionId") Long auctionId);

    List<Bid> findAllByIsSold(boolean isSold);

    List<Bid> findAllByFranchiseIdAndAuctionPlayer_Auction_Id(Long franchiseId, Long auctionId);


}
