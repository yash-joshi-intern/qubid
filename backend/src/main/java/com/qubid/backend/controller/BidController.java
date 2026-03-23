package com.qubid.backend.controller;

import com.qubid.backend.Response.ApiResponse;
import com.qubid.backend.dtos.Response.BidDTO;
import com.qubid.backend.dtos.Response.BidGroupedDTO;
import com.qubid.backend.services.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bid")
@RequiredArgsConstructor
public class BidController {
    private final BidService bidService;

    @GetMapping("/{bidId}")
    public ResponseEntity<ApiResponse<BidDTO>> getById(@PathVariable Long bidId) {

        BidDTO result = bidService.getById(bidId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Bid fetched successfully"));
    }

    @GetMapping("/auction-player/{auctionPlayerId}")
    public ResponseEntity<ApiResponse<List<BidDTO>>> getAllByAuctionPlayer(@PathVariable Long auctionPlayerId) {

        List<BidDTO> result = bidService.getBidHistoryByAuctionPlayer(auctionPlayerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Bids fetched successfully"));
    }

    @GetMapping("/auction-player/{auctionPlayerId}/highest")
    public ResponseEntity<ApiResponse<BidDTO>> getHighestBid(@PathVariable Long auctionPlayerId) {

        BidDTO result = bidService.getHighestBid(auctionPlayerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Highest bid fetched successfully"));
    }


    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<ApiResponse<List<BidDTO>>> getAllByAuction(@PathVariable Long auctionId) {

        List<BidDTO> result = bidService.getAllByAuction(auctionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Auction bids fetched successfully"));
    }

    @GetMapping("/auction/{auctionId}/grouped")
    public ResponseEntity<ApiResponse<List<BidGroupedDTO>>> getAllByAuctionGrouped(@PathVariable Long auctionId) {

        List<BidGroupedDTO> result = bidService.getAllByAuctionGrouped(auctionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Grouped bids fetched successfully"));
    }

    @GetMapping("/franchise/{franchiseId}")
    public ResponseEntity<ApiResponse<List<BidDTO>>> getAllByFranchise(@PathVariable Long franchiseId) {

        List<BidDTO> result = bidService.getAllByFranchise(franchiseId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Franchise bids fetched successfully"));
    }

    @GetMapping("/franchise/{franchiseId}/auction/{auctionId}")
    public ResponseEntity<ApiResponse<List<BidDTO>>> getByFranchiseAndAuction(
            @PathVariable Long franchiseId,
            @PathVariable Long auctionId) {

        List<BidDTO> result = bidService.getByFranchiseAndAuction(franchiseId, auctionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Franchise auction bids fetched successfully"));
    }

    @GetMapping("/sold")
    public ResponseEntity<ApiResponse<List<BidDTO>>> getSoldBids() {

        List<BidDTO> result = bidService.getSoldBids();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Sold bids fetched successfully"));
    }

    @GetMapping("/unsold")
    public ResponseEntity<ApiResponse<List<BidDTO>>> getUnsoldBids() {

        List<BidDTO> result = bidService.getUnsoldBids();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Unsold bids fetched successfully"));
    }
}
