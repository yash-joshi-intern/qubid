package com.qubid.backend.services.Impl;

import com.qubid.backend.dtos.Request.SkillRequestDto;
import com.qubid.backend.dtos.Response.SkillDto;
import com.qubid.backend.dtos.Response.SkillResponseDto;
import com.qubid.backend.entities.Skill;
import com.qubid.backend.repository.SkillRepository;
import com.qubid.backend.services.SkillService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SkillServiceImpl implements SkillService {

    private static final String SKILL_NOT_FOUND = "Skill not found";

    private final SkillRepository skillRepository;
    private final ModelMapper modelMapper;

    @Override
    public SkillResponseDto createSkill(SkillRequestDto dto) {

        if (skillRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new RuntimeException("Skill already exists");
        }

        Skill skill = modelMapper.map(dto, Skill.class);

        Skill savedSkill = skillRepository.save(skill);

        return convertToResponseDto(savedSkill);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillDto> getAllSkills() {
        return skillRepository.findAll()
                .stream()
                .map(skill -> modelMapper.map(skill, SkillDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SkillDto getSkillById(Long id) {
        return modelMapper.map(getSkillOrThrow(id), SkillDto.class);
    }

    @Override
    public SkillResponseDto updateSkill(Long id, SkillRequestDto dto) {

        Skill existingSkill = getSkillOrThrow(id);

        modelMapper.map(dto, existingSkill); // auto update

        Skill updatedSkill = skillRepository.save(existingSkill);

        return convertToResponseDto(updatedSkill);
    }

    @Override
    public void deleteSkill(Long id) {
        getSkillOrThrow(id);
        skillRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SkillDto> getSkillByName(String name) {
        return skillRepository.findByNameIgnoreCase(name)
                .map(skill -> modelMapper.map(skill, SkillDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillDto> searchSkillsByName(String namePart) {
        return skillRepository.searchByName(namePart)
                .stream()
                .map(skill -> modelMapper.map(skill, SkillDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillDto> getSkillsByExpertiseLevel(String expertiseLevel) {
        return skillRepository.findAllByExpertiseLevelIgnoreCase(expertiseLevel)
                .stream()
                .map(skill -> modelMapper.map(skill, SkillDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillDto> getSkillByMaximumRating(Integer ratings) {
        return skillRepository.findAllByRatingLessThanEqual(ratings)
                .stream()
                .map(skill -> modelMapper.map(skill, SkillDto.class))
                .toList();
    }

    private Skill getSkillOrThrow(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(SKILL_NOT_FOUND));
    }

    // Custom mapping (important)
    private SkillResponseDto convertToResponseDto(Skill skill) {
        SkillResponseDto dto = modelMapper.map(skill, SkillResponseDto.class);

        // manual field (not auto-mapped)
        dto.setPlayersCount(
                skill.getPlayers() == null ? 0 : skill.getPlayers().size()
        );

        return dto;
    }
}
