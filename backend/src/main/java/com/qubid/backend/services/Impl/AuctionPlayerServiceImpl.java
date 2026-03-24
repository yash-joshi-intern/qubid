package com.qubid.backend.services.impl;

import com.qubid.backend.dtos.Request.AuctionPlayerRequestDTO;
import com.qubid.backend.dtos.Response.AuctionPlayerDTO;
import com.qubid.backend.entities.Auction;
import com.qubid.backend.entities.AuctionPlayer;
import com.qubid.backend.entities.BasePrice;
import com.qubid.backend.entities.Player;
import com.qubid.backend.enums.AuctionPlayerStatus;
import com.qubid.backend.enums.AuctionStatus;
import com.qubid.backend.repository.AuctionPlayerRepository;
import com.qubid.backend.repository.AuctionRepository;
import com.qubid.backend.repository.BasePriceRepository;
import com.qubid.backend.repository.PlayerRepository;
import com.qubid.backend.services.AuctionPlayerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionPlayerServiceImpl implements AuctionPlayerService {

    private final AuctionPlayerRepository auctionPlayerRepository;
    private final AuctionRepository auctionRepository;
    private final PlayerRepository playerRepository;
    private final BasePriceRepository basePriceRepository;


    @Override
    public AuctionPlayerDTO addToAuction(AuctionPlayerRequestDTO auctionPlayerRequestDTO) {

        AuctionPlayer auctionPlayer = new AuctionPlayer();

        Auction auction = auctionRepository.findById(auctionPlayerRequestDTO.getAuctionId()).orElseThrow(() -> new EntityNotFoundException("No Auction present with id: " + auctionPlayerRequestDTO.getAuctionId()));

        if (auction.getStatus() == AuctionStatus.ENDED) {
            throw new IllegalStateException("Cannot add players to an ENDED auction.");
        }

        auctionPlayer.setAuction(auction);

        Player player = playerRepository.findById(auctionPlayerRequestDTO.getPlayerId()).orElseThrow(() -> new EntityNotFoundException("No Player present with ID:" + auctionPlayerRequestDTO.getPlayerId()));
        if (auctionPlayerRepository.existsByAuctionIdAndPlayerId(auction.getId(), player.getId())) {
            throw new IllegalArgumentException(player.getFirstName() + " is already in this auction.");
        }
        auctionPlayer.setPlayer(player);

        BasePrice basePrice = BasePrice.builder()
                .player(player)
                .tournament(auction.getTournament())
                .basePrice(auctionPlayerRequestDTO.getBasePrice())
                .build();

        BasePrice save = basePriceRepository.save(basePrice);

        auctionPlayer.setBasePrice(save);

        AuctionPlayer newAuctionPlayer = auctionPlayerRepository.save(auctionPlayer);
        return toAuctionPlayerDto(newAuctionPlayer);
    }

    @Override
    public AuctionPlayerDTO getById(Long auctionPlayerId) {
        AuctionPlayer ap = findAuctionPlayer(auctionPlayerId);
        return toAuctionPlayerDto(ap);
    }

    @Override
    public Page<AuctionPlayerDTO> getAllByAuction(Long auctionId, int page, int size, String sortBy, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return auctionPlayerRepository
                .findByAuctionId(auctionId, pageable)
                .map(this::toAuctionPlayerDto);
    }

    @Override
    public List<AuctionPlayerDTO> getByAuctionAndStatus(Long auctionId, AuctionPlayerStatus status) {

        return auctionPlayerRepository
                .findAllByAuctionIdAndStatus(auctionId, status)
                .stream()
                .map(this::toAuctionPlayerDto)
                .toList();

    }

    @Override
    public AuctionPlayerDTO getLivePlayer(Long auctionId) {
        AuctionPlayer auctionPlayer = auctionPlayerRepository.findByAuctionIdAndStatus(auctionId, AuctionPlayerStatus.LIVE).orElseThrow(() -> new EntityNotFoundException("No LIVE player found"));
        return toAuctionPlayerDto(auctionPlayer);
    }

    @Override
    public List<AuctionPlayerDTO> getSoldPlayers(Long auctionId) {
        List<AuctionPlayer> auctionPlayers = auctionPlayerRepository.findAllByAuctionIdAndStatus(auctionId, AuctionPlayerStatus.SOLD);
        return auctionPlayers
                .stream()
                .map(this::toAuctionPlayerDto)
                .toList();
    }

    @Override
    public List<AuctionPlayerDTO> getUnsoldPlayers(Long auctionId) {
        List<AuctionPlayer> auctionPlayers = auctionPlayerRepository.findAllByAuctionIdAndStatus(auctionId, AuctionPlayerStatus.UNSOLD);
        return auctionPlayers
                .stream()
                .map(this::toAuctionPlayerDto)
                .toList();
    }

    @Override
    public List<AuctionPlayerDTO> getPendingPlayers(Long auctionId) {
        List<AuctionPlayer> auctionPlayers = auctionPlayerRepository.findAllByAuctionIdAndStatus(auctionId, AuctionPlayerStatus.PENDING);
        return auctionPlayers
                .stream()
                .map(this::toAuctionPlayerDto)
                .toList();
    }

    @Override
    public List<AuctionPlayerDTO> getByFranchise(Long auctionId, Long franchiseId) {

        List<AuctionPlayer> auctionPlayers = auctionPlayerRepository.findAllByAuctionIdAndSoldToFranchiseId(auctionId, franchiseId);

        return auctionPlayers
                .stream()
                .map(this::toAuctionPlayerDto)
                .toList();
    }

    @Override
    public AuctionPlayerDTO updateBasePrice(Long auctionPlayerId, BigInteger newBasePrice) {
        AuctionPlayer auctionPlayer = findAuctionPlayer(auctionPlayerId);
        if (auctionPlayer.getStatus() != AuctionPlayerStatus.PENDING) {
            throw new IllegalStateException("Can't chnage base price as player status is not pending");
        }
        auctionPlayer.getBasePrice().setBasePrice(newBasePrice);
//        auctionPlayerRepository.save(auctionPlayer);
        basePriceRepository.save(auctionPlayer.getBasePrice());
        return toAuctionPlayerDto(auctionPlayer);
    }

    @Override
    public AuctionPlayerDTO reAuction(Long auctionPlayerId) {
        AuctionPlayer ap = findAuctionPlayer(auctionPlayerId);

        if (ap.getStatus() != AuctionPlayerStatus.UNSOLD) {
            throw new IllegalStateException(
                    "Only UNSOLD players can be re-auctioned. Current status: " + ap.getStatus()
            );
        }

        if (ap.getAuction().getStatus() != AuctionStatus.LIVE) {
            throw new IllegalStateException("Auction is not LIVE. Cannot re-auction player.");
        }

        ap.setStatus(AuctionPlayerStatus.PENDING);
        return toAuctionPlayerDto(auctionPlayerRepository.save(ap));
    }


    private AuctionPlayerDTO toAuctionPlayerDto(AuctionPlayer auctionPlayer) {
        AuctionPlayerDTO dto = new AuctionPlayerDTO();

        // AuctionPlayer basic fields
        dto.setAuctionPlayerId(auctionPlayer.getId());
        dto.setStatus(auctionPlayer.getStatus());
        dto.setFinalSoldPrice(auctionPlayer.getFinalSoldPrice());

        // Player details
        if (auctionPlayer.getPlayer() != null) {
            dto.setPlayerId(auctionPlayer.getPlayer().getId());
            dto.setFirstName(auctionPlayer.getPlayer().getFirstName());
            dto.setLastName(auctionPlayer.getPlayer().getLastName());
            dto.setCountry(auctionPlayer.getPlayer().getCountry());
        }

        // Base Price
        if (auctionPlayer.getBasePrice() != null) {
            dto.setBasePrice(auctionPlayer.getBasePrice().getBasePrice());
        }

        // Auction details
        if (auctionPlayer.getAuction() != null) {
            dto.setAuctionId(auctionPlayer.getAuction().getId());
            dto.setAuctionTitle(auctionPlayer.getAuction().getTitle());
        }

        // Sold Franchise details
        if (auctionPlayer.getSoldToFranchise() != null) {
            dto.setSoldToFranchiseId(auctionPlayer.getSoldToFranchise().getId());
            dto.setSoldToFranchiseName(auctionPlayer.getSoldToFranchise().getName());
        }

        return dto;

    }

    private AuctionPlayer findAuctionPlayer(Long auctionPlayerId) {
        AuctionPlayer auctionPlayer = auctionPlayerRepository.findById(auctionPlayerId).orElseThrow(() -> new EntityNotFoundException("No AuctionPlayer found with id: " + auctionPlayerId));
        return auctionPlayer;
    }
}
