package com.qubid.backend.services;

import com.qubid.backend.dtos.request.FranchiseRequestDTO;
import com.qubid.backend.dtos.response.FranchiseDTO;
import com.qubid.backend.dtos.response.FranchiseResponseDTO;

import java.util.List;
import java.util.Optional;

public interface FranchiseService {

    // Create a new franchise
    FranchiseResponseDTO createFranchise(FranchiseRequestDTO franchise);

    // Fetch all franchises (basic data)
    List<FranchiseDTO> getAllFranchises();

    // Get single franchise by ID (throws if not found)
    FranchiseDTO getFranchiseById(Long id);

    // Update existing franchise by ID
    FranchiseResponseDTO updateFranchise(Long id, FranchiseRequestDTO franchise);

    // Delete franchise by ID
    void deleteFranchise(Long id);

    // --- Optional / Extended Operations ---

    // Fetch franchise by name (case-insensitive expected)
    // Default: not implemented
    default Optional<FranchiseDTO> getFranchiseByName(String name) {
        return Optional.empty();
    }

    // Fetch all franchises by country
    // Default: returns empty list
    default List<FranchiseDTO> getFranchisesByCountry(String country) {
        return List.of();
    }

    // Search franchises using partial name match
    // Default: returns empty list
    default List<FranchiseDTO> searchFranchisesByName(String namePart) {
        return List.of();
    }

    // Fetch franchise with full details (e.g., joins / nested data)
    FranchiseResponseDTO getFranchiseByIdWithDetails(Long id);

    // Fetch all franchises with full details
    List<FranchiseResponseDTO> getAllFranchisesWithDetails();
}