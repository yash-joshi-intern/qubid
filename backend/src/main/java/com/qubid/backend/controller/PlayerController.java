package com.qubid.backend.controller;

import com.qubid.backend.Response.ApiResponse;
import com.qubid.backend.dtos.request.PlayerRequestDTO;
import com.qubid.backend.dtos.response.PlayerResponseDTO;
import com.qubid.backend.dtos.response.SkillResponseDTO;
import com.qubid.backend.dtos.response.TeamResponseDTO;
import com.qubid.backend.dtos.response.TournamentResponseDTO;
import com.qubid.backend.services.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addPlayer(
            @RequestBody @Valid PlayerRequestDTO requestDTO) {

        String result = playerService.addPlayer(requestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Player created successfully"));
    }

    @PostMapping("/add/batch")
    public ResponseEntity<ApiResponse<String>> addListOfPlayers(
            @RequestBody @Valid List<PlayerRequestDTO> playerDTOList) {

        String result = playerService.addListOfPlayer(playerDTOList);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Players created successfully"));
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<ApiResponse<PlayerResponseDTO>> getPlayerById(
            @PathVariable Long playerId) {

        PlayerResponseDTO result = playerService.getPlayerById(playerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Player fetched successfully"));
    }

    @GetMapping("/batch")
    public ResponseEntity<ApiResponse<List<PlayerResponseDTO>>> getListOfPlayers(
            @RequestParam List<Long> playerIdList) {

        List<PlayerResponseDTO> result = playerService.getListOfPlayer(playerIdList);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Players fetched successfully"));
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<ApiResponse<PlayerResponseDTO>> updatePlayer(
            @PathVariable Long playerId,
            @RequestBody @Valid PlayerRequestDTO requestDTO) {

        PlayerResponseDTO result = playerService.updatePlayer(playerId, requestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Player updated successfully"));
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<ApiResponse<String>> deletePlayer(
            @PathVariable Long playerId) {

        String result = playerService.deletePlayer(playerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Player deleted successfully"));
    }

    @DeleteMapping("/batch")
    public ResponseEntity<ApiResponse<String>> deleteListOfPlayers(
            @RequestBody List<Long> playerIdList) {

        String result = playerService.deleteListOfPlayer(playerIdList);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Players deleted successfully"));
    }

    @GetMapping("/{playerId}/skills")
    public ResponseEntity<ApiResponse<List<SkillResponseDTO>>> getSkillsForPlayer(
            @PathVariable Long playerId) {

        List<SkillResponseDTO> result = playerService.getListOfSkillsForPlayerID(playerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Skills fetched successfully"));
    }

    @GetMapping("/{playerId}/teams")
    public ResponseEntity<ApiResponse<List<TeamResponseDTO>>> getTeamsForPlayer(
            @PathVariable Long playerId) {

        List<TeamResponseDTO> result = playerService.getListOfTeamForPlayerID(playerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Teams fetched successfully"));
    }


    @GetMapping("/{playerId}/tournaments")
    public ResponseEntity<ApiResponse<List<TournamentResponseDTO>>> getTournamentsForPlayer(
            @PathVariable Long playerId) {

        List<TournamentResponseDTO> result = playerService.getListOfTournamentForPlayerID(playerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Tournaments fetched successfully"));
    }

    @PostMapping("/{playerId}/tournament/{tournamentId}/auction")
    public ResponseEntity<ApiResponse<String>> addLivePlayerInTournament(
            @PathVariable Long playerId,
            @PathVariable Long tournamentId,
            @RequestParam BigInteger basePrice) {

        String result = playerService.addLivePlayerInTournament(playerId, tournamentId, basePrice);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Player added to auction successfully"));
    }
}