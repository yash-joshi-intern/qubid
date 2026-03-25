package com.qubid.backend.controller;

import com.qubid.backend.Response.ApiResponse;
import com.qubid.backend.dtos.Request.BasePriceRequestDTO;
import com.qubid.backend.dtos.Response.BasePriceResponseDTO;
import com.qubid.backend.services.BasePriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/base-price")
@RequiredArgsConstructor
public class BasePriceController {

    private final BasePriceService basePriceService;

    @PostMapping
    public ResponseEntity<ApiResponse<BasePriceResponseDTO>> create(
            @RequestBody @Valid BasePriceRequestDTO dto) {

        BasePriceResponseDTO result = basePriceService.createBasePrice(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Base price created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BasePriceResponseDTO>> getById(@PathVariable Long id) {

        BasePriceResponseDTO result = basePriceService.getBasePriceById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Base price fetched successfully"));
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<ApiResponse<List<BasePriceResponseDTO>>> getByPlayer(
            @PathVariable Long playerId) {

        List<BasePriceResponseDTO> result = basePriceService.getBasePricesByPlayer(playerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Base prices for player fetched successfully"));
    }

    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<ApiResponse<List<BasePriceResponseDTO>>> getByTournament(
            @PathVariable Long tournamentId) {

        List<BasePriceResponseDTO> result = basePriceService.getBasePricesByTournament(tournamentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Base prices for tournament fetched successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BasePriceResponseDTO>> update(
            @PathVariable Long id,
            @RequestBody @Valid BasePriceRequestDTO dto) {

        BasePriceResponseDTO result = basePriceService.updateBasePrice(id, dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Base price updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {

        basePriceService.deleteBasePrice(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(null, "Base price deleted successfully"));
    }
}
