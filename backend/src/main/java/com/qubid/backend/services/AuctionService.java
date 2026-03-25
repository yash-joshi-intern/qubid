package com.qubid.backend.services;

import com.qubid.backend.dtos.Request.AuctionRequestDTO;
import com.qubid.backend.dtos.Request.PlaceBidRequestDTO;
import com.qubid.backend.dtos.Response.AuctionDTO;

import java.util.List;

public interface AuctionService {
    AuctionDTO createAuction(AuctionRequestDTO auctionRequestDto);

    void startAuction(Long auctionId);

    void goLive(Long auctionId, Long auctionPlayerId);

    void placeBid(Long auctionId, PlaceBidRequestDTO request);

    void markSold(Long auctionId, Long auctionPlayerId);

    void markUnsold(Long auctionId, Long auctionPlayerId);

    void endAuction(Long auctionId);

    AuctionDTO getById(Long auctionId);

    List<AuctionDTO> getAll();

}
