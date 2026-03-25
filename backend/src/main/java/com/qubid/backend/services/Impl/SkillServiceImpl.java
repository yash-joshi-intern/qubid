package com.qubid.backend.services.Impl;

import com.qubid.backend.dtos.request.SkillRequestDTO;
import com.qubid.backend.dtos.response.SkillDTO;
import com.qubid.backend.dtos.response.SkillPlayerDTO;
import com.qubid.backend.dtos.response.SkillPlayerRowDTO;
import com.qubid.backend.dtos.response.SkillResponseDTO;
import com.qubid.backend.entities.Player;
import com.qubid.backend.entities.Skill;
import com.qubid.backend.repository.SkillRepository;
import com.qubid.backend.services.SkillService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private static final String SKILL_NOT_FOUND = "Skill not found";

    private final SkillRepository skillRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public SkillResponseDTO createSkill(SkillRequestDTO dto) {

        if (skillRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Skill already exists with name: " + dto.getName());
        }

        Skill skill = modelMapper.map(dto, Skill.class);

        Skill savedSkill = skillRepository.save(skill);

        return convertToResponseDto(savedSkill);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillDTO> getAllSkills() {
        return skillRepository.findAll().stream().map(skill -> modelMapper.map(skill, SkillDTO.class)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SkillDTO getSkillById(Long id) {
        return modelMapper.map(getSkillOrThrow(id), SkillDTO.class);
    }

    @Override
    @Transactional
    public SkillResponseDTO updateSkill(Long id, SkillRequestDTO dto) {

        Skill existingSkill = getSkillOrThrow(id);

        modelMapper.map(dto, existingSkill); // auto update

        Skill updatedSkill = skillRepository.save(existingSkill);

        return convertToResponseDto(updatedSkill);
    }

    @Override
    @Transactional
    public void deleteSkill(Long id) {
        getSkillOrThrow(id);
        skillRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public SkillPlayerDTO getPlayersBySkillId(Long id) {

        getSkillOrThrow(id);

        List<Player> players =
                skillRepository.findPlayerRowsBySkillId(id)
                        .stream()
                        .map(SkillPlayerRowDTO::getPlayer)
                        .filter(Objects::nonNull)
                        .toList();

        return new SkillPlayerDTO(id, players);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SkillDTO> getSkillByName(String name) {
        return skillRepository.findByNameIgnoreCase(name).map(skill -> modelMapper.map(skill, SkillDTO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillDTO> searchSkillsByName(String namePart) {
        return skillRepository.findByNameContainingIgnoreCase(namePart).stream().map(skill -> modelMapper.map(skill, SkillDTO.class)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillDTO> getSkillsByExpertiseLevel(String expertiseLevel) {
        return skillRepository.findAllByExpertiseLevelIgnoreCase(expertiseLevel).stream().map(skill -> modelMapper.map(skill, SkillDTO.class)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillDTO> getSkillByMaximumRating(Integer ratings) {
        return skillRepository.findAllByRatingLessThanEqual(ratings).stream().map(skill -> modelMapper.map(skill, SkillDTO.class)).toList();
    }

    private Skill getSkillOrThrow(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Skill not found with id: " + id));
    }

    // Custom mapping (important)
    private SkillResponseDTO convertToResponseDto(Skill skill) {
        SkillResponseDTO dto = modelMapper.map(skill, SkillResponseDTO.class);

        // manual field (not auto-mapped)
        dto.setPlayersCount(skill.getPlayers() == null ? 0 : skill.getPlayers().size());

        return dto;
    }
}
