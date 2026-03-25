package com.qubid.backend.services;

import com.qubid.backend.dtos.request.AuctionPlayerRequestDTO;
import com.qubid.backend.dtos.response.AuctionPlayerDTO;
import com.qubid.backend.enums.AuctionPlayerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.math.BigInteger;
import java.util.List;

public interface AuctionPlayerService {

    AuctionPlayerDTO addToAuction(AuctionPlayerRequestDTO auctionPlayerRequestDTO);

    AuctionPlayerDTO getById(Long auctionPlayerId);

    Page<AuctionPlayerDTO> getAllByAuction(Long auctionId, int page, int size, String sortBy, Sort.Direction direction);

    List<AuctionPlayerDTO> getByAuctionAndStatus(Long auctionId, AuctionPlayerStatus status);

    AuctionPlayerDTO getLivePlayer(Long auctionId);

    List<AuctionPlayerDTO> getSoldPlayers(Long auctionId);

    List<AuctionPlayerDTO> getUnsoldPlayers(Long auctionId);

    List<AuctionPlayerDTO> getPendingPlayers(Long auctionId);

    List<AuctionPlayerDTO> getByFranchise(Long auctionId, Long franchiseId);

    AuctionPlayerDTO updateBasePrice(Long auctionPlayerId, BigInteger newBasePrice);

    AuctionPlayerDTO reAuction(Long auctionPlayerId); // Unsold to pending

}
