package com.qubid.backend.services.Impl;

import com.qubid.backend.dtos.Response.BidDTO;
import com.qubid.backend.dtos.Response.BidGroupedDTO;
import com.qubid.backend.entities.Auction;
import com.qubid.backend.entities.AuctionPlayer;
import com.qubid.backend.entities.Bid;
import com.qubid.backend.repository.AuctionRepository;
import com.qubid.backend.repository.BidRepository;
import com.qubid.backend.services.BidService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;

    @Override
    public List<BidDTO> getBidHistoryByAuctionPlayer(Long auctionPlayerId) {
        List<Bid> bids = bidRepository.findAllByAuctionPlayerId(auctionPlayerId);
        return bids
                .stream()
                .map(this::toBidDTO)
                .toList();
    }

    @Override
    public BidDTO getHighestBid(Long auctionPlayerId) {
        Bid bid = bidRepository.findTopByAuctionPlayerIdOrderByCurrentPriceDesc(auctionPlayerId).orElseThrow(() -> new EntityNotFoundException("No Bid FOund"));
        return toBidDTO(bid);
    }

    @Override
    public List<BidDTO> getAllByAuction(Long auctionId) {
        return bidRepository
                .findAllByAuctionPlayer_AuctionId(auctionId)
                .stream()
                .map(this::toBidDTO)
                .toList();
    }

    @Override
    public List<BidGroupedDTO> getAllByAuctionGrouped(Long auctionId) {

        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new EntityNotFoundException("No auction Found"));
        List<AuctionPlayer> auctionPlayerList = auction.getAuctionPlayerList();

        return auctionPlayerList
                .stream()
                .map(auctionPlayer -> {
                    BidGroupedDTO bidGroupedDTO = new BidGroupedDTO();
                    bidGroupedDTO.setAuctionPlayerId(auctionPlayer.getId());
                    bidGroupedDTO.setPlayerName(auctionPlayer.getPlayer().getFirstName() + " " + auctionPlayer.getPlayer().getLastName());
                    bidGroupedDTO.setPlayerStatus(auctionPlayer.getStatus());

                    Bid highestBid = bidRepository
                            .findTopByAuctionPlayerIdOrderByCurrentPriceDesc(auctionPlayer.getId())
                            .orElse(null);

                    bidGroupedDTO.setHighestBid(highestBid != null ? highestBid.getCurrentPrice() : null);
                    bidGroupedDTO.setHighestBidFranchise(highestBid != null ? highestBid.getFranchise().getName() : null);
                    bidGroupedDTO.setBids(auctionPlayer
                            .getBids()
                            .stream()
                            .map(this::toBidDTO)
                            .toList()
                    );
                    bidGroupedDTO.setTotalBids(bidGroupedDTO.getBids().size());
                    return bidGroupedDTO;
                })
                .toList();
    }

    @Override
    public List<BidDTO> getAllByFranchise(Long franchiseId) {
        return bidRepository
                .findAllByFranchiseId(franchiseId)
                .stream()
                .map(this::toBidDTO)
                .toList();

    }

    @Override
    public List<BidDTO> getByFranchiseAndAuction(Long franchiseId, Long auctionId) {
        return bidRepository
                .findAllByFranchiseIdAndAuctionPlayer_Auction_Id(franchiseId, auctionId)
                .stream()
                .map(this::toBidDTO)
                .toList();
    }

    @Override
    public List<BidDTO> getSoldBids() {
        return bidRepository
                .findAllByIsSold(true)
                .stream()
                .map(this::toBidDTO)
                .toList();
    }

    @Override
    public List<BidDTO> getUnsoldBids() {
        return bidRepository
                .findAllByIsSold(false)
                .stream()
                .map(this::toBidDTO)
                .toList();
    }

    @Override
    public BidDTO getById(Long bidId) {
        Bid bid = bidRepository.findById(bidId).orElseThrow(() -> new EntityNotFoundException("No bid found with id: " + bidId));
        return toBidDTO(bid);
    }

    private BidDTO toBidDTO(Bid bid) {
        BidDTO bidDTO = new BidDTO();
        bidDTO.setBidAmount(bid.getCurrentPrice());
        bidDTO.setSold(bid.isSold());
        bidDTO.setFranchiseId(bid.getFranchise().getId());
        bidDTO.setFranchiseName(bid.getFranchise().getName());
        bidDTO.setPlayerName(bid.getAuctionPlayer().getPlayer().getFirstName() + " " + bid.getAuctionPlayer().getPlayer().getLastName());
        bidDTO.setAuctionPlayerId(bid.getAuctionPlayer().getId());
        bidDTO.setBidId(bid.getId());

        return bidDTO;
    }


}
