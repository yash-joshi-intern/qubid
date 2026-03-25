package com.qubid.backend.services.Impl;

import com.qubid.backend.dtos.request.FranchiseRequestDTO;
import com.qubid.backend.dtos.response.FranchiseDTO;
import com.qubid.backend.dtos.response.FranchiseResponseDTO;
import com.qubid.backend.dtos.response.FranchiseTeamRowDTO;
import com.qubid.backend.dtos.response.FranchiseTournamentRowDTO;
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
    public FranchiseResponseDTO createFranchise(FranchiseRequestDTO dto) {

        if (franchiseRepository.existsFranchiseByName(dto.getName())) {
            throw new IllegalArgumentException("Franchise already exists with this name");
        }

        Franchise franchise = modelMapper.map(dto, Franchise.class);

        Franchise saved = franchiseRepository.save(franchise);

        return modelMapper.map(saved, FranchiseResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FranchiseDTO> getAllFranchises() {
        return franchiseRepository.findAll()
                .stream()
                .map(f -> modelMapper.map(f, FranchiseDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public FranchiseDTO getFranchiseById(Long id) {
        return modelMapper.map(getFranchiseOrThrow(id), FranchiseDTO.class);
    }

    @Override
    @Transactional
    public FranchiseResponseDTO updateFranchise(Long id, FranchiseRequestDTO dto) {

        Franchise existing = getFranchiseOrThrow(id);

        modelMapper.map(dto, existing); // auto update fields

        Franchise updated = franchiseRepository.save(existing);

        return modelMapper.map(updated, FranchiseResponseDTO.class);
    }

    @Override
    @Transactional
    public void deleteFranchise(Long id) {
        getFranchiseOrThrow(id);
        franchiseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<FranchiseDTO> getFranchiseByName(String name) {
        return Optional.of(franchiseRepository.findByNameIgnoreCase(name)
                .map(f -> modelMapper.map(f, FranchiseDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("Franchise not found with name: " + name)));
    }

    @Override
    @Transactional
    public List<FranchiseDTO> getFranchisesByCountry(String country) {
        return franchiseRepository.findAllByCountryIgnoreCase(country)
                .stream()
                .map(f -> modelMapper.map(f, FranchiseDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public List<FranchiseDTO> searchFranchisesByName(String namePart) {
        return franchiseRepository.findByNameContainingIgnoreCase(namePart)
                .stream()
                .map(f -> modelMapper.map(f, FranchiseDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public FranchiseResponseDTO getFranchiseByIdWithDetails(Long id) {
        FranchiseDTO franchise = franchiseRepository.findFranchiseDtoById(id)
                .orElseThrow(() -> new EntityNotFoundException(FRANCHISE_NOT_FOUND));

        // TODO: Teams Dto will replace with entities
        List<Team> teams = franchiseRepository.findTeamRowsByFranchiseId(id)
                .stream()
                .map(FranchiseTeamRowDTO::getTeam)
                .filter(Objects::nonNull)
                .toList();

        // TODO: Tournament Dto will replace with entities
        List<Tournament> tournaments = franchiseRepository.findTournamentRowsByFranchiseId(id)
                .stream()
                .map(FranchiseTournamentRowDTO::getTournament)
                .filter(Objects::nonNull)
                .toList();

        return new FranchiseResponseDTO(
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
    public List<FranchiseResponseDTO> getAllFranchisesWithDetails() {
        List<FranchiseDTO> franchises = franchiseRepository.findAllFranchiseDtos();
        if (franchises.isEmpty()) {
            return List.of();
        }

        List<Long> franchiseIds = franchises.stream()
                .map(FranchiseDTO::getId)
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
                .map(franchise -> new FranchiseResponseDTO(
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
