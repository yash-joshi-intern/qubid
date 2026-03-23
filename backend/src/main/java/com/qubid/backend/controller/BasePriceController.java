package com.qubid.backend.controller;

import com.qubid.backend.dtos.Request.BasePriceRequestDTO;
import com.qubid.backend.dtos.Response.BasePriceResponseDTO;
import com.qubid.backend.services.BasePriceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/base-price")
public class BasePriceController {

    private final BasePriceService basePriceService;

    public BasePriceController(BasePriceService basePriceService) {
        this.basePriceService = basePriceService;
    }

    /*
     * POST /api/base-price
     * Create a new base price for a player in a tournament.
     */
    @PostMapping
    public ResponseEntity<BasePriceResponseDTO> addBasePrice(
            @RequestBody @Valid BasePriceRequestDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(basePriceService.addBasePrice(requestDTO));
    }

    /*
     * GET /api/base-price/{id}
     * Fetch a single base price record by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BasePriceResponseDTO> getBasePriceById(@PathVariable Long id) {
        return ResponseEntity.ok(basePriceService.getBasePriceById(id));
    }

    /*
     * GET /api/base-price/player/{playerId}
     * Fetch all base prices for a given player across all tournaments.
     */
    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<BasePriceResponseDTO>> getBasePricesByPlayer(
            @PathVariable Long playerId) {
        return ResponseEntity.ok(basePriceService.getBasePricesByPlayerId(playerId));
    }

    /*
     * GET /api/base-price/tournament/{tournamentId}
     * Fetch all base prices set within a given tournament.
     */
    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<List<BasePriceResponseDTO>> getBasePricesByTournament(
            @PathVariable Long tournamentId) {
        return ResponseEntity.ok(basePriceService.getBasePricesByTournamentId(tournamentId));
    }

    /*
     * GET /api/base-price/player/{playerId}/tournament/{tournamentId}
     * Fetch the base price for a specific player in a specific tournament.
     */
    @GetMapping("/player/{playerId}/tournament/{tournamentId}")
    public ResponseEntity<BasePriceResponseDTO> getBasePriceByPlayerAndTournament(
            @PathVariable Long playerId,
            @PathVariable Long tournamentId) {
        return ResponseEntity.ok(basePriceService.getBasePriceByPlayerAndTournament(playerId, tournamentId));
    }

    /*
     * PUT /api/base-price/{id}
     * Update an existing base price record.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BasePriceResponseDTO> updateBasePrice(
            @PathVariable Long id,
            @RequestBody @Valid BasePriceRequestDTO requestDTO) {
        return ResponseEntity.ok(basePriceService.updateBasePrice(id, requestDTO));
    }

    /*
     * DELETE /api/base-price/{id}
     * Delete a base price record by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBasePrice(@PathVariable Long id) {
        return ResponseEntity.ok(basePriceService.deleteBasePrice(id));
    }
}
