package com.qubid.backend.controller;

import com.qubid.backend.Response.ApiResponse;
import com.qubid.backend.entities.Player;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/player")
public class PlayerController {
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Player>> addPlayer(@RequestBody @Valid Player player){
        //for testing
//        throw new Exception("some exception ocurreddddddd");
        return ResponseEntity.status(HttpStatus.CREATED).body( new ApiResponse<>(player, "player created"));
    }

}
