package com.qubid.backend.controller;

import com.qubid.backend.Response.ApiResponse;
import com.qubid.backend.dtos.request.TournamentRequestDTO;
import com.qubid.backend.dtos.response.FranchiseResponseForTournamentDTO;
import com.qubid.backend.dtos.response.PlayerResponseDTO;
import com.qubid.backend.dtos.response.TeamResponseDTO;
import com.qubid.backend.dtos.response.TournamentResponseDTO;
import com.qubid.backend.services.TournamentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addTournament(
            @RequestBody @Valid TournamentRequestDTO requestDTO) {

        String result = tournamentService.addTournament(requestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Tournament created successfully"));
    }

    @PostMapping("/add/batch")
    public ResponseEntity<ApiResponse<String>> addListOfTournaments(
            @RequestBody @Valid List<TournamentRequestDTO> tournamentList) {

        String result = tournamentService.addListOfTournament(tournamentList);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Tournaments created successfully"));
    }

    @GetMapping("/{tournamentId}")
    public ResponseEntity<ApiResponse<TournamentResponseDTO>> getTournament(
            @PathVariable Long tournamentId) {

        TournamentResponseDTO result = tournamentService.getTournament(tournamentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Tournament fetched successfully"));
    }

    @GetMapping("/batch")
    public ResponseEntity<ApiResponse<List<TournamentResponseDTO>>> getListOfTournaments(
            @RequestParam List<Long> tournamentIdList) {

        List<TournamentResponseDTO> result = tournamentService.getListOfTournamentId(tournamentIdList);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Tournaments fetched successfully"));
    }

    @PutMapping("/{tournamentId}")
    public ResponseEntity<ApiResponse<TournamentResponseDTO>> updateTournament(
            @PathVariable Long tournamentId,
            @RequestBody @Valid TournamentRequestDTO requestDTO) {

        TournamentResponseDTO result = tournamentService.updateTournament(tournamentId, requestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Tournament updated successfully"));
    }

    @DeleteMapping("/{tournamentId}")
    public ResponseEntity<ApiResponse<String>> deleteTournament(
            @PathVariable Long tournamentId) {

        String result = tournamentService.deleteTournament(tournamentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Tournament deleted successfully"));
    }

    @DeleteMapping("/batch")
    public ResponseEntity<ApiResponse<String>> deleteListOfTournaments(
            @RequestBody List<Long> tournamentIdList) {

        String result = tournamentService.deleteListOfTournament(tournamentIdList);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Tournaments deleted successfully"));
    }

    @GetMapping("/{tournamentId}/players")
    public ResponseEntity<ApiResponse<List<PlayerResponseDTO>>> getPlayersByTournament(
            @PathVariable Long tournamentId) {

        List<PlayerResponseDTO> result = tournamentService.getPlayerListByTournamentId(tournamentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Players fetched successfully"));
    }

    @GetMapping("/{tournamentId}/franchises")
    public ResponseEntity<ApiResponse<List<FranchiseResponseForTournamentDTO>>> getFranchisesByTournament(
            @PathVariable Long tournamentId) {

        List<FranchiseResponseForTournamentDTO> result = tournamentService.getFranchiseListByTournamentId(tournamentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Franchises fetched successfully"));
    }

    @GetMapping("/{tournamentId}/teams")
    public ResponseEntity<ApiResponse<List<TeamResponseDTO>>> getTeamsByTournament(
            @PathVariable Long tournamentId) {

        List<TeamResponseDTO> result = tournamentService.getTeamListByTournamentId(tournamentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Teams fetched successfully"));
    }


    @PostMapping("/{tournamentId}/teams/batch")
    public ResponseEntity<ApiResponse<String>> addTeamsToTournament(
            @PathVariable Long tournamentId,
            @RequestBody List<Long> teamIds) {

        String result = tournamentService.addListOfTeamInTournament(tournamentId, teamIds);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Teams added to tournament successfully"));
    }

    @PutMapping("/{tournamentId}/teams/batch")
    public ResponseEntity<ApiResponse<String>> updateTeamsInTournament(
            @PathVariable Long tournamentId,
            @RequestBody List<Long> teamIds) {

        String result = tournamentService.updateListOfTeamInTournament(tournamentId, teamIds);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Tournament teams updated successfully"));
    }

    @DeleteMapping("/{tournamentId}/teams")
    public ResponseEntity<ApiResponse<String>> deleteAllTeamsFromTournament(
            @PathVariable Long tournamentId) {

        String result = tournamentService.deleteTournamentTeamList(tournamentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "All teams unlinked from tournament successfully"));
    }
}