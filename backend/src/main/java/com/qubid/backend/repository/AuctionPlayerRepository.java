package com.qubid.backend.repository;

import com.qubid.backend.entities.AuctionPlayer;
import com.qubid.backend.enums.AuctionPlayerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionPlayerRepository extends JpaRepository<AuctionPlayer, Long> {
    List<AuctionPlayer> findByAuctionIdOrderByIdAsc(Long auctionId);

    //for live
    Optional<AuctionPlayer> findByAuctionIdAndStatus(Long auctionId, AuctionPlayerStatus auctionPlayerStatus);

    //for sold unsold pending
    List<AuctionPlayer> findAllByAuctionIdAndStatus(Long auctionId, AuctionPlayerStatus status);

    List<AuctionPlayer> findAllByAuctionIdAndSoldToFranchiseId(Long auctionId, Long franchiseId);

    Boolean existsByAuctionIdAndPlayerId(Long auctionId, Long PlayerId);

    Page<AuctionPlayer> findByAuctionId(Long auctionId, Pageable pageable);

}
