package com.qubid.backend.controller;

import com.qubid.backend.Response.ApiResponse;
import com.qubid.backend.dtos.Request.FranchiseRequestDto;
import com.qubid.backend.dtos.Response.FranchiseDto;
import com.qubid.backend.dtos.Response.FranchiseResponseDto;
import com.qubid.backend.services.FranchiseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/franchise")
@RequiredArgsConstructor
public class FranchiseController {
    private final FranchiseService franchiseService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<FranchiseResponseDto>> addFranchise(@RequestBody @Valid FranchiseRequestDto franchiseRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(franchiseService.createFranchise(franchiseRequestDto), "Franchise Created"));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<FranchiseResponseDto>> updateFranchise(@RequestParam Long id, @RequestBody @Valid FranchiseRequestDto franchiseRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(franchiseService.updateFranchise(id, franchiseRequestDto), "Franchise Updated"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFranchise(@RequestParam Long id) {
        franchiseService.deleteFranchise(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<FranchiseDto>>> getAllFranchises() {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(franchiseService.getAllFranchises(), "Franchises List"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FranchiseDto>> getFranchiseById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(franchiseService.getFranchiseById(id), "Franchise Fetched"));
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ApiResponse<FranchiseResponseDto>> getFranchiseByIdWithDetails(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(franchiseService.getFranchiseByIdWithDetails(id), "Franchise Details Fetched"));
    }

    @GetMapping("/all/details")
    public ResponseEntity<ApiResponse<List<FranchiseResponseDto>>> getAllFranchisesWithDetails() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(franchiseService.getAllFranchisesWithDetails(), "Franchise Details List"));
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<ApiResponse<Optional<FranchiseDto>>> getFranchiseByName(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(franchiseService.getFranchiseByName(name), "Franchise Fetched"));
    }

    @GetMapping("/search-by-country")
    public ResponseEntity<ApiResponse<List<FranchiseDto>>> getFranchisesByCountry(@RequestParam String country) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(franchiseService.getFranchisesByCountry(country), "Franchises By Country"));
    }

    @GetMapping("/search-by-name-like")
    public ResponseEntity<ApiResponse<List<FranchiseDto>>> searchFranchisesByName(@RequestParam String namePart) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(franchiseService.searchFranchisesByName(namePart), "Franchise Search Result"));
    }

}
