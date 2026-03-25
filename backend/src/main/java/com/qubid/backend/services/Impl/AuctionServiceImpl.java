package com.qubid.backend.services.impl;

import com.qubid.backend.ExceptionHandler.InsufficientPurseException;
import com.qubid.backend.dtos.request.AuctionRequestDTO;
import com.qubid.backend.dtos.request.PlaceBidRequestDTO;
import com.qubid.backend.dtos.response.*;
import com.qubid.backend.entities.*;
import com.qubid.backend.enums.AuctionPlayerStatus;
import com.qubid.backend.enums.AuctionStatus;
import com.qubid.backend.repository.*;
import com.qubid.backend.services.AuctionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionPlayerRepository auctionPlayerRepository;
    private final TournamentRepository tournamentRepository;
    private final FranchiseRepository franchiseRepository;
    private final BidRepository bidRepository;
    private final ModelMapper modelMapper;

    private final SimpMessagingTemplate broker;
    private final TeamRepository teamRepository;


    @Override
    public AuctionDTO createAuction(AuctionRequestDTO auctionRequestDto) {
        Auction auction = new Auction();
        auction.setStatus(auctionRequestDto.getStatus());
        auction.setVenue(auctionRequestDto.getVenue());
        auction.setTitle(auctionRequestDto.getTitle());
        auction.setEventDate(auctionRequestDto.getEventDate());

        Tournament tournament = tournamentRepository.findById(auctionRequestDto.getTournamentId()).orElseThrow(() -> new RuntimeException("Tournament Not Found"));

        auctionRepository.findAllByTournamentId(auctionRequestDto.getTournamentId()).ifPresent((a) -> {
            throw new IllegalArgumentException("Auction is Already created for tournamnet with auctionId : " + a.getId());
        });
        auction.setTournament(tournament);
        auctionRepository.save(auction);

        //set all franchise purse to touranment alloted purse

        List<Franchise> franchises = tournament.getFranchises();
        franchises.forEach(franchise -> {
            Team team1 = teamRepository
                    .findByFranchiseIdAndTournamentId(franchise.getId(), tournament.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Team Not Found"));

            team1.setRemainingPurse(tournament.getAllotedPurse());
        });
        return modelMapper.map(auction, AuctionDTO.class);
    }

    @Override
    public void startAuction(Long auctionId) {
        Auction auction = findAuction(auctionId);

        if (auction.getStatus() == AuctionStatus.LIVE) {
            throw new IllegalStateException("Auction " + auctionId + " is already live");
        }

        auction.setStatus(AuctionStatus.LIVE);
        auctionRepository.save(auction);
    }

    @Override
    public void goLive(Long auctionId, Long auctionPlayerId) {
        Auction auction = findAuction(auctionId);
        isAuctionLive(auction);

        auctionPlayerRepository.findByAuctionIdAndStatus(auctionId, AuctionPlayerStatus.LIVE).ifPresent(player -> {
            throw new IllegalStateException("player " + player.getPlayer().getFirstName() + " with id: " + player.getId() + " is already Live");
        });

        AuctionPlayer auctionPlayer = findAuctionPlayer(auctionPlayerId);
        isAuctionPlayerBelongsToAuction(auctionPlayer, auctionId);

        if (auctionPlayer.getStatus() != AuctionPlayerStatus.PENDING) {
            throw new IllegalStateException("Auction Player " + auctionPlayer.getPlayer().getFirstName() + " is Not Pending");
        }

        auctionPlayer.setStatus(AuctionPlayerStatus.LIVE);

        PlayerLiveEventDTO playerLiveEventDTO = PlayerLiveEventDTO.builder()
                .auctionPlayerId(auctionPlayerId)
                .playerId(auctionPlayer.getPlayer().getId())
                .firstName(auctionPlayer.getPlayer().getFirstName())
                .lastName(auctionPlayer.getPlayer().getLastName())
                .country(auctionPlayer.getPlayer().getCountry())
                .basePrice(auctionPlayer.getBasePrice().getBasePrice())
                .status(auctionPlayer.getStatus())
                .build();

        broker.convertAndSend("/topic/auction/" + auctionId + "/player-live", playerLiveEventDTO);
    }

    @Override
    public void placeBid(Long auctionId, PlaceBidRequestDTO request) {
        Auction auction = findAuction(auctionId);
        isAuctionLive(auction);

        AuctionPlayer auctionPlayer = findAuctionPlayer(request.getAuctionPlayerId());
        isAuctionPlayerBelongsToAuction(auctionPlayer, auctionId);

        if (auctionPlayer.getStatus() != AuctionPlayerStatus.LIVE) {
            throw new IllegalStateException("auction player " + auctionPlayer.getId() + " is not Live For Auction yet");
        }

        Franchise franchise = franchiseRepository.findById(request.getFranchiseId()).orElseThrow(() ->
                new EntityNotFoundException("Franchise Not FOund : " + request.getFranchiseId())
        );

        Team team1 = teamRepository
                .findByFranchiseIdAndTournamentId(franchise.getId(), auction.getTournament().getId())
                .orElseThrow(() -> new EntityNotFoundException("No team Found For franchise " + franchise.getName() + "and tournament " + auction.getTournament().getName()));

        if (team1.getRemainingPurse().compareTo(request.getBidAmount()) < 0) {
            throw new InsufficientPurseException("Franchise Don't have enough purse to bid for the player " + auctionPlayer.getPlayer().getFirstName());
        }

        BigDecimal currentHighest = bidRepository
                .findTopByAuctionPlayerIdOrderByCurrentPriceDesc(request.getAuctionPlayerId())
                .map(Bid::getCurrentPrice)
                .orElse(BigDecimal.ZERO);

        // if first bid then higher then baseprice
        BigDecimal minRequired = currentHighest.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal(auctionPlayer.getBasePrice().getBasePrice()) : currentHighest;

        if (request.getBidAmount().compareTo(minRequired) <= 0) {
            throw new IllegalArgumentException("bid Amount must be higher then " + minRequired);
        }

        Bid bid = new Bid();
        bid.setCurrentPrice(request.getBidAmount());
        bid.setFranchise(franchise);
        bid.setAuctionPlayer(auctionPlayer);
        bid.setSold(false);

        Bid savedBid = bidRepository.save(bid);

        // broadcast part

        NewBidEventDTO bidEventDTO = NewBidEventDTO.builder()
                .bidId(savedBid.getId())
                .auctionPlayerId(auctionPlayer.getId())
                .franchiseId(franchise.getId())
                .franchiseName(franchise.getName())
                .currentHighest(request.getBidAmount())
                .build();

        broker.convertAndSend("/topic/auction/" + auctionId + "/new-bid", bidEventDTO);
    }

    @Override
    public void markSold(Long auctionId, Long auctionPlayerId) {
        Auction auction = findAuction(auctionId);
        isAuctionLive(auction);

        AuctionPlayer auctionPlayer = findAuctionPlayer(auctionPlayerId);
        isAuctionPlayerBelongsToAuction(auctionPlayer, auctionId);

        if (auctionPlayer.getStatus() != AuctionPlayerStatus.LIVE) {
            throw new IllegalStateException("AuctionPlayer is not LIVE, cannot mark as SOLD.");
        }

        Bid highestBid = bidRepository
                .findTopByAuctionPlayerIdOrderByCurrentPriceDesc(auctionPlayerId)
                .orElseThrow(() -> new IllegalStateException("No bids placed. Use markUnsold instead."));

        highestBid.setSold(true);
        Franchise franchise = highestBid.getFranchise();

        Team team = teamRepository
                .findByFranchiseIdAndTournamentId(franchise.getId(), auction.getTournament().getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Team Not Found with franchis id: " + franchise.getId() + "and tournament id:" + auction.getTournament().getId())
                );

        auctionPlayer.setStatus(AuctionPlayerStatus.SOLD);
        auctionPlayer.setSoldToFranchise(franchise);
        auctionPlayer.setFinalSoldPrice(highestBid.getCurrentPrice());
        auctionPlayerRepository.save(auctionPlayer);

        team.setRemainingPurse(team.getRemainingPurse().subtract(highestBid.getCurrentPrice()));

        Player player = auctionPlayer.getPlayer();
        if (!team.getPlayers().contains(player)) {
            team.getPlayers().add(player);
        }
        teamRepository.save(team);

        //broadcast part

        PlayerSoldEventDTO playerSoldEventDTO = PlayerSoldEventDTO.builder()
                .auctionPlayerId(auctionPlayerId)
                .playerId(player.getId())
                .playerName(player.getFirstName() + " " + player.getLastName())
                .soldToFranchiseId(franchise.getId())
                .soldToFranchiseName(franchise.getName())
                .finalPrice(highestBid.getCurrentPrice())
                .franchiseRemainingPurse(team.getRemainingPurse())
                .status(auctionPlayer.getStatus())
                .build();

        broker.convertAndSend("/topic/auction/" + auctionId + "/sold", playerSoldEventDTO);

    }

    @Override
    public void markUnsold(Long auctionId, Long auctionPlayerId) {
        Auction auction = findAuction(auctionId);
        isAuctionLive(auction);

        AuctionPlayer auctionPlayer = findAuctionPlayer(auctionPlayerId);
        isAuctionPlayerBelongsToAuction(auctionPlayer, auctionId);

        if (auctionPlayer.getStatus() != AuctionPlayerStatus.LIVE) {
            throw new IllegalStateException("AuctionPlayer is not LIVE, cannot mark as UNSOLD.");
        }

        auctionPlayer.setStatus(AuctionPlayerStatus.UNSOLD);
        auctionPlayerRepository.save(auctionPlayer);

        // Broadcast
        PlayerUnsoldEventDTO playerUnsoldEventDTO = PlayerUnsoldEventDTO.builder()
                .auctionPlayerId(auctionPlayerId)
                .playerId(auctionPlayer.getPlayer().getId())
                .playerName(
                        auctionPlayer.getPlayer().getFirstName() + " " +
                                auctionPlayer.getPlayer().getLastName()
                )
                .status(auctionPlayer.getStatus())
                .build();


        broker.convertAndSend("/topic/auction/" + auctionId + "/unsold", playerUnsoldEventDTO);
    }

    @Override
    public void endAuction(Long auctionId) {
        Auction auction = findAuction(auctionId);
        isAuctionLive(auction);

        long count = auction
                .getAuctionPlayerList()
                .stream()
                .filter(auctionPlayer -> auctionPlayer.getStatus() == AuctionPlayerStatus.LIVE)
                .count();
        if (count > 0) {
            throw new IllegalStateException("some players are still LIVE, mark then unsold");
        }
        auction.setStatus(AuctionStatus.ENDED);
        auctionRepository.save(auction);
    }

    @Override
    public AuctionDTO getById(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new EntityNotFoundException("No Auction found with ID:" + auctionId));
        AuctionDTO auctionDTO = modelMapper.map(auction, AuctionDTO.class);
        auctionDTO.setAuctionPlayers(
                auction
                        .getAuctionPlayerList()
                        .stream()
                        .map(this::toAuctionPlayerDto)
                        .toList()
        );
        return auctionDTO;
    }

    @Override
    public List<AuctionDTO> getAll() {
        List<Auction> auctions = auctionRepository.findAll();
        List<AuctionDTO> list = auctions.stream().map(auction -> {
            AuctionDTO auctionDTO = modelMapper.map(auction, AuctionDTO.class);
            List<AuctionPlayerDTO> list1 = auction.getAuctionPlayerList().stream().map(this::toAuctionPlayerDto).toList();
            auctionDTO.setAuctionPlayers(list1);
            return auctionDTO;
        }).toList();

        return list;
    }


    private Auction findAuction(Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new EntityNotFoundException("Auction not found: " + auctionId));
    }

    private AuctionPlayer findAuctionPlayer(Long auctionPlayerId) {
        return auctionPlayerRepository.findById(auctionPlayerId)
                .orElseThrow(() -> new EntityNotFoundException("AuctionPlayer not found: " + auctionPlayerId));
    }

    private void isAuctionLive(Auction auction) {
        if (auction.getStatus() != AuctionStatus.LIVE) {
            throw new IllegalStateException(
                    "Auction " + auction.getId() + " is not LIVE (status=" + auction.getStatus() + ")."
            );
        }
    }


    private void isAuctionPlayerBelongsToAuction(AuctionPlayer auctionPlayer, Long auctionId) {
        if (!auctionPlayer.getAuction().getId().equals(auctionId)) {
            throw new IllegalArgumentException(
                    "AuctionPlayer " + auctionPlayer.getId() + " does not belong to auction " + auctionId
            );
        }
    }

    private AuctionPlayerDTO toAuctionPlayerDto(AuctionPlayer auctionPlayer) {

        return AuctionPlayerDTO.builder()
                // AuctionPlayer basic fields
                .auctionPlayerId(auctionPlayer.getId())
                .status(auctionPlayer.getStatus())
                .finalSoldPrice(auctionPlayer.getFinalSoldPrice())

                // Player details
                .playerId(auctionPlayer.getPlayer() != null ? auctionPlayer.getPlayer().getId() : null)
                .firstName(auctionPlayer.getPlayer() != null ? auctionPlayer.getPlayer().getFirstName() : null)
                .lastName(auctionPlayer.getPlayer() != null ? auctionPlayer.getPlayer().getLastName() : null)
                .country(auctionPlayer.getPlayer() != null ? auctionPlayer.getPlayer().getCountry() : null)

                // Base Price
                .basePrice(auctionPlayer.getBasePrice() != null ? auctionPlayer.getBasePrice().getBasePrice() : null)

                // Auction details
                .auctionId(auctionPlayer.getAuction() != null ? auctionPlayer.getAuction().getId() : null)
                .auctionTitle(auctionPlayer.getAuction() != null ? auctionPlayer.getAuction().getTitle() : null)

                // Sold Franchise details
                .soldToFranchiseId(
                        auctionPlayer.getSoldToFranchise() != null
                                ? auctionPlayer.getSoldToFranchise().getId()
                                : null
                )
                .soldToFranchiseName(
                        auctionPlayer.getSoldToFranchise() != null
                                ? auctionPlayer.getSoldToFranchise().getName()
                                : null
                )

                .build();
    }

}
