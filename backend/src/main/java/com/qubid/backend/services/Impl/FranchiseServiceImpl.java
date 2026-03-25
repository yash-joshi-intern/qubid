package com.qubid.backend.services.Impl;

import com.qubid.backend.dtos.Request.FranchiseRequestDto;
import com.qubid.backend.dtos.Response.FranchiseDto;
import com.qubid.backend.dtos.Response.FranchiseResponseDto;
import com.qubid.backend.dtos.Response.FranchiseTeamRowDto;
import com.qubid.backend.dtos.Response.FranchiseTournamentRowDto;
import com.qubid.backend.entities.Franchise;
import com.qubid.backend.entities.Team;
import com.qubid.backend.entities.Tournament;
import com.qubid.backend.repository.FranchiseRepository;
import com.qubid.backend.services.FranchiseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FranchiseServiceImpl implements FranchiseService {

    private static final String FRANCHISE_NOT_FOUND = "Franchise not found";

    private final FranchiseRepository franchiseRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public FranchiseResponseDto createFranchise(FranchiseRequestDto dto) {

        if (franchiseRepository.existsFranchiseByName(dto.getName())) {
            throw new IllegalArgumentException("Franchise already exists with this name");
        }

        Franchise franchise = modelMapper.map(dto, Franchise.class);

        Franchise saved = franchiseRepository.save(franchise);

        return modelMapper.map(saved, FranchiseResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FranchiseDto> getAllFranchises() {
        return franchiseRepository.findAll()
                .stream()
                .map(f -> modelMapper.map(f, FranchiseDto.class))
                .toList();
    }

    @Override
    @Transactional
    public FranchiseDto getFranchiseById(Long id) {
        return modelMapper.map(getFranchiseOrThrow(id), FranchiseDto.class);
    }

    @Override
    @Transactional
    public FranchiseResponseDto updateFranchise(Long id, FranchiseRequestDto dto) {

        Franchise existing = getFranchiseOrThrow(id);

        modelMapper.map(dto, existing); // auto update fields

        Franchise updated = franchiseRepository.save(existing);

        return modelMapper.map(updated, FranchiseResponseDto.class);
    }

    @Override
    @Transactional
    public void deleteFranchise(Long id) {
        getFranchiseOrThrow(id);
        franchiseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<FranchiseDto> getFranchiseByName(String name) {
        return Optional.of(franchiseRepository.findByNameIgnoreCase(name)
                .map(f -> modelMapper.map(f, FranchiseDto.class))
                .orElseThrow(() -> new EntityNotFoundException("Franchise not found with name: " + name)));
    }

    @Override
    @Transactional
    public List<FranchiseDto> getFranchisesByCountry(String country) {
        return franchiseRepository.findAllByCountryIgnoreCase(country)
                .stream()
                .map(f -> modelMapper.map(f, FranchiseDto.class))
                .toList();
    }

    @Override
    @Transactional
    public List<FranchiseDto> searchFranchisesByName(String namePart) {
        return franchiseRepository.findByNameContainingIgnoreCase(namePart)
                .stream()
                .map(f -> modelMapper.map(f, FranchiseDto.class))
                .toList();
    }

    @Override
    @Transactional
    public FranchiseResponseDto getFranchiseByIdWithDetails(Long id) {
        FranchiseDto franchise = franchiseRepository.findFranchiseDtoById(id)
                .orElseThrow(() -> new EntityNotFoundException(FRANCHISE_NOT_FOUND));

        // TODO: Teams Dto will replace with entities
        List<Team> teams = franchiseRepository.findTeamRowsByFranchiseId(id)
                .stream()
                .map(FranchiseTeamRowDto::getTeam)
                .filter(Objects::nonNull)
                .toList();

        // TODO: Tournament Dto will replace with entities
        List<Tournament> tournaments = franchiseRepository.findTournamentRowsByFranchiseId(id)
                .stream()
                .map(FranchiseTournamentRowDto::getTournament)
                .filter(Objects::nonNull)
                .toList();

        return new FranchiseResponseDto(
                franchise.getId(),
                franchise.getName(),
                franchise.getCity(),
                franchise.getCountry(),
                franchise.getContact(),
                teams,
                tournaments
        );
    }

    @Override
    @Transactional
    public List<FranchiseResponseDto> getAllFranchisesWithDetails() {
        List<FranchiseDto> franchises = franchiseRepository.findAllFranchiseDtos();
        if (franchises.isEmpty()) {
            return List.of();
        }

        List<Long> franchiseIds = franchises.stream()
                .map(FranchiseDto::getId)
                .toList();

        // TODO: Teams Dto will replace with entities
        Map<Long, List<Team>> teamsByFranchiseId = new HashMap<>();
        franchiseRepository.findTeamRowsByFranchiseIds(franchiseIds)
                .forEach(row -> {
                    // TODO: Teams Dto will replace with entities
                    Team team = row.getTeam();
                    if (team != null) {
                        teamsByFranchiseId
                                .computeIfAbsent(row.getFranchiseId(), ignored -> new ArrayList<>())
                                .add(team);
                    }
                });

        // TODO: Tournament Dto will replace with entities
        Map<Long, List<Tournament>> tournamentsByFranchiseId = new HashMap<>();
        franchiseRepository.findTournamentRowsByFranchiseIds(franchiseIds)
                .forEach(row -> {
                    // TODO: Tournament Dto will replace with entities
                    Tournament tournament = row.getTournament();
                    if (tournament != null) {
                        tournamentsByFranchiseId
                                .computeIfAbsent(row.getFranchiseId(), ignored -> new ArrayList<>())
                                .add(tournament);
                    }
                });

        return franchises.stream()
                .map(franchise -> new FranchiseResponseDto(
                        franchise.getId(),
                        franchise.getName(),
                        franchise.getCity(),
                        franchise.getCountry(),
                        franchise.getContact(),
                        teamsByFranchiseId.getOrDefault(franchise.getId(), List.of()),
                        tournamentsByFranchiseId.getOrDefault(franchise.getId(), List.of())
                ))
                .toList();
    }

    private Franchise getFranchiseOrThrow(Long id) {
        return franchiseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Franchise not found with id: " + id));
    }
}
