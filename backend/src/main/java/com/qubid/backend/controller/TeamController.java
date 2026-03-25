package com.qubid.backend.controller;


import com.qubid.backend.Response.ApiResponse;
import com.qubid.backend.dtos.request.TeamRequestDTO;
import com.qubid.backend.dtos.response.PlayerResponseDTO;
import com.qubid.backend.dtos.response.TeamResponseDTO;
import com.qubid.backend.services.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addTeam(
            @RequestBody @Valid TeamRequestDTO requestDTO) {

        String result = teamService.addTeam(requestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Team created successfully"));
    }

    @PostMapping("/add/batch")
    public ResponseEntity<ApiResponse<String>> addMultipleTeams(
            @RequestBody @Valid List<TeamRequestDTO> teamList) {

        String result = teamService.addMultipleTeams(teamList);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Teams created successfully"));
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<ApiResponse<TeamResponseDTO>> getTeam(
            @PathVariable Long teamId) {

        TeamResponseDTO result = teamService.getTeam(teamId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Team fetched successfully"));
    }

    @GetMapping("/batch")
    public ResponseEntity<ApiResponse<List<TeamResponseDTO>>> getListOfTeams(
            @RequestParam List<Long> teamIds) {

        List<TeamResponseDTO> result = teamService.getListOfTeam(teamIds);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Teams fetched successfully"));
    }

    @GetMapping("/{teamId}/remaining-slots")
    public ResponseEntity<ApiResponse<Integer>> getRemainingPlayerSlots(
            @PathVariable Long teamId) {

        Integer result = teamService.getRemainingPlayerSlots(teamId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Remaining player slots fetched successfully"));
    }


    @PutMapping("/{teamId}")
    public ResponseEntity<ApiResponse<TeamResponseDTO>> modifyTeam(
            @PathVariable Long teamId,
            @RequestBody @Valid TeamRequestDTO requestDTO) {

        TeamResponseDTO result = teamService.modifyTeam(teamId, requestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Team updated successfully"));
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<ApiResponse<String>> deleteTeam(
            @PathVariable Long teamId) {

        String result = teamService.deleteTeam(teamId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Team deleted successfully"));
    }

    @PostMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<ApiResponse<String>> addOnePlayerInTeam(
            @PathVariable Long teamId,
            @PathVariable Long playerId) {

        String result = teamService.addOnePlayerInTeam(teamId, playerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Player added to team successfully"));
    }

    @PostMapping("/{teamId}/players/batch")
    public ResponseEntity<ApiResponse<String>> addListOfPlayersInTeam(
            @PathVariable Long teamId,
            @RequestBody List<Long> playerIds) {

        String result = teamService.addListOfPlayerInTeam(teamId, playerIds);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Players added to team successfully"));
    }

    @GetMapping("/{teamId}/players")
    public ResponseEntity<ApiResponse<List<PlayerResponseDTO>>> getPlayersInTeam(
            @PathVariable Long teamId) {

        List<PlayerResponseDTO> result = teamService.getListOfPlayerForTeam(teamId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Players fetched successfully"));
    }

    @DeleteMapping("/{teamId}/players/batch")
    public ResponseEntity<ApiResponse<String>> deletePlayersFromTeam(
            @PathVariable Long teamId,
            @RequestBody List<Long> playerIds) {

        String result = teamService.deleteListOfPlayerInTeam(teamId, playerIds);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(result, "Players removed from team successfully"));
    }
}