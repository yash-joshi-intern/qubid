package com.qubid.backend.controller;

import com.qubid.backend.Response.ApiResponse;
import com.qubid.backend.dtos.Request.AuctionRequestDTO;
import com.qubid.backend.dtos.Request.PlaceBidRequestDTO;
import com.qubid.backend.dtos.Response.AuctionDTO;
import com.qubid.backend.services.AuctionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
public class AuctionController {
    private final AuctionService auctionService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AuctionDTO>> createAuction(@RequestBody @Valid AuctionRequestDTO auctionRequestDTO) {

        AuctionDTO created = auctionService.createAuction(auctionRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Auction created successfully"));
    }

    @PostMapping("/{auctionId}/start")
    public ResponseEntity<ApiResponse<Void>> startAuction(@PathVariable Long auctionId) {

        auctionService.startAuction(auctionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(null, "Auction started successfully"));
    }

    @PostMapping("/{auctionId}/player/{auctionPlayerId}/live")
    public ResponseEntity<ApiResponse<Void>> goLive(
            @PathVariable Long auctionId,
            @PathVariable Long auctionPlayerId) {

        auctionService.goLive(auctionId, auctionPlayerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(null, "Player is now LIVE for bidding"));
    }

    @PostMapping("/{auctionId}/bid")
    public ResponseEntity<ApiResponse<Void>> placeBid(
            @PathVariable Long auctionId,
            @RequestBody @Valid PlaceBidRequestDTO placeBidRequestDTO) {

        auctionService.placeBid(auctionId, placeBidRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(null, "Bid placed successfully"));
    }

    @PostMapping("/{auctionId}/player/{auctionPlayerId}/sold")
    public ResponseEntity<ApiResponse<Void>> markSold(
            @PathVariable Long auctionId,
            @PathVariable Long auctionPlayerId) {

        auctionService.markSold(auctionId, auctionPlayerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(null, "Player marked as SOLD"));
    }

    @PostMapping("/{auctionId}/player/{auctionPlayerId}/unsold")
    public ResponseEntity<ApiResponse<Void>> markUnsold(
            @PathVariable Long auctionId,
            @PathVariable Long auctionPlayerId) {

        auctionService.markUnsold(auctionId, auctionPlayerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(null, "Player marked as UNSOLD"));
    }

    @PostMapping("/{auctionId}/end")
    public ResponseEntity<ApiResponse<Void>> endAuction(
            @PathVariable Long auctionId) {

        auctionService.endAuction(auctionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(null, "Auction ended successfully"));
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<ApiResponse<AuctionDTO>> findById(
            @PathVariable Long auctionId) {

        AuctionDTO auctionDto = auctionService.getById(auctionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(auctionDto, "Auction fetched successfully"));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AuctionDTO>>> findAll(
    ) {

        List<AuctionDTO> auctionDto = auctionService.getAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(auctionDto, "all Auctions fetched successfully"));
    }


}
