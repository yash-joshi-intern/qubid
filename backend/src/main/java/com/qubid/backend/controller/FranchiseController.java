package com.qubid.backend.controller;

import com.qubid.backend.Response.ApiResponse;
import com.qubid.backend.dtos.request.FranchiseRequestDTO;
import com.qubid.backend.dtos.response.FranchiseDTO;
import com.qubid.backend.dtos.response.FranchiseResponseDTO;
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
    public ResponseEntity<ApiResponse<FranchiseResponseDTO>> addFranchise(@RequestBody @Valid FranchiseRequestDTO franchiseRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(franchiseService.createFranchise(franchiseRequestDto), "Franchise Created"));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<FranchiseResponseDTO>> updateFranchise(@RequestParam Long id, @RequestBody @Valid FranchiseRequestDTO franchiseRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(franchiseService.updateFranchise(id, franchiseRequestDto), "Franchise Updated"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFranchise(@RequestParam Long id) {
        franchiseService.deleteFranchise(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<FranchiseDTO>>> getAllFranchises() {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(franchiseService.getAllFranchises(), "Franchises List"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FranchiseDTO>> getFranchiseById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(franchiseService.getFranchiseById(id), "Franchise Fetched"));
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ApiResponse<FranchiseResponseDTO>> getFranchiseByIdWithDetails(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(franchiseService.getFranchiseByIdWithDetails(id), "Franchise Details Fetched"));
    }

    @GetMapping("/all/details")
    public ResponseEntity<ApiResponse<List<FranchiseResponseDTO>>> getAllFranchisesWithDetails() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(franchiseService.getAllFranchisesWithDetails(), "Franchise Details List"));
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<ApiResponse<Optional<FranchiseDTO>>> getFranchiseByName(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(franchiseService.getFranchiseByName(name), "Franchise Fetched"));
    }

    @GetMapping("/search-by-country")
    public ResponseEntity<ApiResponse<List<FranchiseDTO>>> getFranchisesByCountry(@RequestParam String country) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(franchiseService.getFranchisesByCountry(country), "Franchises By Country"));
    }

    @GetMapping("/search-by-name-like")
    public ResponseEntity<ApiResponse<List<FranchiseDTO>>> searchFranchisesByName(@RequestParam String namePart) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(franchiseService.searchFranchisesByName(namePart), "Franchise Search Result"));
    }

}
