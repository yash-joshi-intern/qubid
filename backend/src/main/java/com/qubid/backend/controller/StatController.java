package com.qubid.backend.controller;

import com.qubid.backend.Response.ApiResponse;
import com.qubid.backend.dtos.Request.StatRequestDTO;
import com.qubid.backend.dtos.Response.StatResponseDTO;
import com.qubid.backend.enums.CricketFormat;
import com.qubid.backend.services.StatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatController {

    private final StatService statService;

    @PostMapping("/players/{playerId}")
    public ResponseEntity<ApiResponse<StatResponseDTO>> createStats(
            @PathVariable Long playerId,
            @Valid @RequestBody StatRequestDTO request
    ) {
        StatResponseDTO data = statService.createStats(playerId, request);

        return ResponseEntity
                .status(201)
                .body(ApiResponse.success(data, "Stats created successfully"));
    }

    @PutMapping("/{statsId}")
    public ResponseEntity<ApiResponse<StatResponseDTO>> updateStats(
            @PathVariable Long statsId,
            @Valid @RequestBody StatRequestDTO request
    ) {
        StatResponseDTO data = statService.updateStats(statsId, request);

        return ResponseEntity.status(200).body(ApiResponse.success(data, "Stats updated Successfully"));
    }

    @GetMapping("/players/{playerId}")
    public ResponseEntity<ApiResponse<List<StatResponseDTO>>> getStatsByPlayer(
            @PathVariable Long playerId
    ) {
        List<StatResponseDTO> data = statService.getStatsByPlayer(playerId);

        return ResponseEntity.ok(
                ApiResponse.success(data, "Stats fetched successfully")
        );
    }

    @GetMapping("/players/by-format/{playerId}")
    public ResponseEntity<ApiResponse<StatResponseDTO>> getStatsByPlayerAndFormat(
            @PathVariable Long playerId,
            @RequestParam CricketFormat format
    ) {
        StatResponseDTO data = statService.getStatsByPlayerAndFormat(playerId, format);

        return ResponseEntity.ok(
                ApiResponse.success(data, "Stats fetched successfully")
        );
    }

    @DeleteMapping("/{statsId}")
    public ResponseEntity<ApiResponse<StatResponseDTO>> deleteStats(@PathVariable Long statsId) {
        statService.deleteStats(statsId);

        return ResponseEntity.ok(
                ApiResponse.success(null, "Stats deleted successfully")
        );
    }
}
