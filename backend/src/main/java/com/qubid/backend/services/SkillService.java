package com.qubid.backend.services;

import com.qubid.backend.dtos.request.SkillRequestDTO;
import com.qubid.backend.dtos.response.SkillDTO;
import com.qubid.backend.dtos.response.SkillPlayerDTO;
import com.qubid.backend.dtos.response.SkillResponseDTO;

import java.util.List;
import java.util.Optional;

public interface SkillService {

    // Create a new skill
    SkillResponseDTO createSkill(SkillRequestDTO skillRequestDto);

    // Fetch all skills (basic)
    List<SkillDTO> getAllSkills();

    // Fetch skill by id (basic)
    SkillDTO getSkillById(Long id);

    // Update skill by id
    SkillResponseDTO updateSkill(Long id, SkillRequestDTO skillRequestDto);

    // Delete skill by id
    void deleteSkill(Long id);

    // Fetch Player by skill id
    public SkillPlayerDTO getPlayersBySkillId(Long id);

    // --- Optional / Extended Operations ---

    // Fetch skill by exact name
    default Optional<SkillDTO> getSkillByName(String name) {
        return Optional.empty();
    }

    // Search skills by partial name
    default List<SkillDTO> searchSkillsByName(String namePart) {
        return List.of();
    }

    // Fetch skills by expertise level
    default List<SkillDTO> getSkillsByExpertiseLevel(String expertiseLevel) {
        return List.of();
    }

    // Fetch by maximum rating
    default List<SkillDTO> getSkillByMaximumRating(Integer ratings) {
        return List.of();
    }

}
