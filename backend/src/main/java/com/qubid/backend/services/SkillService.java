package com.qubid.backend.services;

import com.qubid.backend.dtos.Request.SkillRequestDto;
import com.qubid.backend.dtos.Response.SkillDto;
import com.qubid.backend.dtos.Response.SkillPlayerDTO;
import com.qubid.backend.dtos.Response.SkillResponseDto;

import java.util.List;
import java.util.Optional;

public interface SkillService {

    // Create a new skill
    SkillResponseDto createSkill(SkillRequestDto skillRequestDto);

    // Fetch all skills (basic)
    List<SkillDto> getAllSkills();

    // Fetch skill by id (basic)
    SkillDto getSkillById(Long id);

    // Update skill by id
    SkillResponseDto updateSkill(Long id, SkillRequestDto skillRequestDto);

    // Delete skill by id
    void deleteSkill(Long id);

    // Fetch Player by skill id
    public SkillPlayerDTO getPlayersBySkillId(Long id);

    // --- Optional / Extended Operations ---

    // Fetch skill by exact name
    default Optional<SkillDto> getSkillByName(String name) {
        return Optional.empty();
    }

    // Search skills by partial name
    default List<SkillDto> searchSkillsByName(String namePart) {
        return List.of();
    }

    // Fetch skills by expertise level
    default List<SkillDto> getSkillsByExpertiseLevel(String expertiseLevel) {
        return List.of();
    }

    // Fetch by maximum rating
    default List<SkillDto> getSkillByMaximumRating(Integer ratings) {
        return List.of();
    }

}
