package com.qubid.backend.controller;

import com.qubid.backend.dtos.Response.ApiResponse;
import com.qubid.backend.entities.Player;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/player")
public class PlayerController {
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Player>> addPlayer(@RequestBody @Valid Player player) {
        //for testing,implementation pending
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<Player>success(player, "player created"));
    }

}

