package com.qubid.backend.services;

import com.qubid.backend.dtos.Request.FranchiseRequestDto;
import com.qubid.backend.dtos.Response.FranchiseDto;
import com.qubid.backend.dtos.Response.FranchiseResponseDto;

import java.util.List;
import java.util.Optional;

public interface FranchiseService {

    // Create a new franchise
    FranchiseResponseDto createFranchise(FranchiseRequestDto franchise);

    // Fetch all franchises (basic data)
    List<FranchiseDto> getAllFranchises();

    // Get single franchise by ID (throws if not found)
    FranchiseDto getFranchiseById(Long id);

    // Update existing franchise by ID
    FranchiseResponseDto updateFranchise(Long id, FranchiseRequestDto franchise);

    // Delete franchise by ID
    void deleteFranchise(Long id);

    // --- Optional / Extended Operations ---

    // Fetch franchise by name (case-insensitive expected)
    // Default: not implemented
    default Optional<FranchiseDto> getFranchiseByName(String name) {
        return Optional.empty();
    }

    // Fetch all franchises by country
    // Default: returns empty list
    default List<FranchiseDto> getFranchisesByCountry(String country) {
        return List.of();
    }

    // Search franchises using partial name match
    // Default: returns empty list
    default List<FranchiseDto> searchFranchisesByName(String namePart) {
        return List.of();
    }

    // Fetch franchise with full details (e.g., joins / nested data)
    FranchiseResponseDto getFranchiseByIdWithDetails(Long id);

    // Fetch all franchises with full details
    List<FranchiseResponseDto> getAllFranchisesWithDetails();
}