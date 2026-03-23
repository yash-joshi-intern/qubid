package com.qubid.backend.controller;

import com.qubid.backend.Response.ApiResponse;
import com.qubid.backend.dtos.Request.AuctionPlayerRequestDTO;
import com.qubid.backend.dtos.Response.AuctionPlayerDTO;
import com.qubid.backend.enums.AuctionPlayerStatus;
import com.qubid.backend.services.AuctionPlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/auction-player")
@RequiredArgsConstructor
public class AuctionPlayerController {

    private final AuctionPlayerService auctionPlayerService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<AuctionPlayerDTO>> addToAuction(@RequestBody @Valid AuctionPlayerRequestDTO auctionPlayerRequestDTO) {

        AuctionPlayerDTO result = auctionPlayerService.addToAuction(auctionPlayerRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Player added to auction successfully"));
    }

    @GetMapping("/{auctionPlayerId}")
    public ResponseEntity<ApiResponse<AuctionPlayerDTO>> getById(@PathVariable Long auctionPlayerId) {

        AuctionPlayerDTO result = auctionPlayerService.getById(auctionPlayerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "AuctionPlayer fetched successfully"));
    }


    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<ApiResponse<Page<AuctionPlayerDTO>>> getAllByAuction(
            @PathVariable Long auctionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {

        Page<AuctionPlayerDTO> result = auctionPlayerService.getAllByAuction(auctionId, page, size, sortBy, direction);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Auction players fetched successfully"));
    }

    @GetMapping("/auction/{auctionId}/status")
    public ResponseEntity<ApiResponse<List<AuctionPlayerDTO>>> getByAuctionAndStatus(
            @PathVariable Long auctionId,
            @RequestParam AuctionPlayerStatus status) {

        List<AuctionPlayerDTO> result = auctionPlayerService.getByAuctionAndStatus(auctionId, status);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, status + " players fetched successfully"));
    }

    @GetMapping("/auction/{auctionId}/live")
    public ResponseEntity<ApiResponse<AuctionPlayerDTO>> getLivePlayer(
            @PathVariable Long auctionId) {

        AuctionPlayerDTO result = auctionPlayerService.getLivePlayer(auctionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Live player fetched successfully"));
    }

    @GetMapping("/auction/{auctionId}/sold")
    public ResponseEntity<ApiResponse<List<AuctionPlayerDTO>>> getSoldPlayers(@PathVariable Long auctionId) {

        List<AuctionPlayerDTO> result = auctionPlayerService.getSoldPlayers(auctionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Sold players fetched successfully"));
    }

    @GetMapping("/auction/{auctionId}/unsold")
    public ResponseEntity<ApiResponse<List<AuctionPlayerDTO>>> getUnsoldPlayers(
            @PathVariable Long auctionId) {

        List<AuctionPlayerDTO> result = auctionPlayerService.getUnsoldPlayers(auctionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Unsold players fetched successfully"));
    }

    @GetMapping("/auction/{auctionId}/pending")
    public ResponseEntity<ApiResponse<List<AuctionPlayerDTO>>> getPendingPlayers(@PathVariable Long auctionId) {

        List<AuctionPlayerDTO> result = auctionPlayerService.getPendingPlayers(auctionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Pending players fetched successfully"));
    }

    @GetMapping("/auction/{auctionId}/franchise/{franchiseId}")
    public ResponseEntity<ApiResponse<List<AuctionPlayerDTO>>> getByFranchise(
            @PathVariable Long auctionId,
            @PathVariable Long franchiseId) {

        List<AuctionPlayerDTO> result = auctionPlayerService.getByFranchise(auctionId, franchiseId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Franchise players fetched successfully"));
    }

    @PutMapping("/{auctionPlayerId}/base-price")
    public ResponseEntity<ApiResponse<AuctionPlayerDTO>> updateBasePrice(
            @PathVariable Long auctionPlayerId,
            @RequestBody BigInteger newBasePrice) {

        AuctionPlayerDTO result = auctionPlayerService.updateBasePrice(auctionPlayerId, newBasePrice);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Base price updated successfully"));
    }

    @PutMapping("/{auctionPlayerId}/re-auction")
    public ResponseEntity<ApiResponse<AuctionPlayerDTO>> reAuction(
            @PathVariable Long auctionPlayerId) {

        AuctionPlayerDTO result = auctionPlayerService.reAuction(auctionPlayerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Player moved back to PENDING for re-auction"));
    }
}