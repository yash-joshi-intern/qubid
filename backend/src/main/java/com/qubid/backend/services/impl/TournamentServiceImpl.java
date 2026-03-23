package com.qubid.backend.services.impl;

import com.qubid.backend.ExceptionHandler.TeamNotFoundException;
import com.qubid.backend.ExceptionHandler.TournamentNotFoundException;
import com.qubid.backend.dto.request.TournamentRequestDTO;
import com.qubid.backend.dto.response.TournamentResponseDTO;
import com.qubid.backend.entities.Franchise;
import com.qubid.backend.entities.Player;
import com.qubid.backend.entities.Team;
import com.qubid.backend.entities.Tournament;
import com.qubid.backend.repository.TeamRepository;
import com.qubid.backend.repository.TournamentRepository;
import com.qubid.backend.services.TournamentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public String addTournament(TournamentRequestDTO requestDTO) {
        validateDates(requestDTO.getStartDate(), requestDTO.getEndDate());
        Tournament tournament = modelMapper.map(requestDTO, Tournament.class);
        tournamentRepository.save(tournament);
        return "Tournament added successfully";
    }

    @Override
    @Transactional
    public String addListOfTournament(List<TournamentRequestDTO> tournamentList) {
        List<Tournament> tournaments = tournamentList.stream()
                .peek(dto -> validateDates(dto.getStartDate(), dto.getEndDate()))
                .map(dto -> modelMapper.map(dto, Tournament.class))
                .toList();

        tournamentRepository.saveAll(tournaments);
        return "Tournaments are added successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public TournamentResponseDTO getTournament(Long tournamentId) {
        Tournament tournament = findTournamentOrThrow(tournamentId);
        return modelMapper.map(tournament, TournamentResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TournamentResponseDTO> getListOfTournamentId(List<Long> tournamentIdList) {
        List<Tournament> tournaments = tournamentRepository.findAllByIds(tournamentIdList);
        if (tournaments.isEmpty()) {
            throw new RuntimeException("No tournaments found for given IDs");
        }
        return tournaments.stream()
                .map(dto -> modelMapper.map(dto, TournamentResponseDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public TournamentResponseDTO updateTournament(Long tournamentId, TournamentRequestDTO requestDTO) {
        Tournament existing = findTournamentOrThrow(tournamentId);

        if (requestDTO.getStartDate() != null && requestDTO.getEndDate() != null) {
            validateDates(requestDTO.getStartDate(), requestDTO.getEndDate());
        }

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(requestDTO, existing);
        existing.setUpdatedAt(Instant.now());

        Tournament saved = tournamentRepository.save(existing);
        return modelMapper.map(saved, TournamentResponseDTO.class);
    }

    @Override
    @Transactional
    public String deleteTournament(Long tournamentId) {
        Tournament tournament = findTournamentOrThrow(tournamentId);
        tournamentRepository.delete(tournament);
        return "Tournament deleted successfully";
    }

    @Override
    @Transactional
    public String deleteListOfTournament(List<Long> listOfTournamentId) {
        List<Tournament> tournaments = tournamentRepository.findAllByIds(listOfTournamentId);

        if (tournaments.isEmpty()) {
            throw new RuntimeException("No tournaments found for given IDs");
        }

        tournamentRepository.deleteAll(tournaments);
        return "Tournaments deleted successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public List<Player> getPlayerListByTournamentId(Long tournamentId) {
        return findTournamentOrThrow(tournamentId).getPlayers();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Franchise> getFranchiseListByTournamentId(Long tournamentId) {
        return findTournamentOrThrow(tournamentId).getFranchises();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Team> getTeamListByTournamentId(Long tournamentId) {
        return findTournamentOrThrow(tournamentId).getTeams();
    }

    @Override
    @Transactional
    public String addListOfTeamInTournament(Long tournamentId, List<Long> teamIds) {
        Tournament tournament = findTournamentOrThrow(tournamentId);
        List<Team> teamsToAdd = fetchTeamsOrThrow(teamIds);

        Set<Long> existingTeamIds = tournament.getTeams()
                .stream()
                .map(t -> t.getId())
                .collect(Collectors.toSet());

        List<Long> duplicates = teamIds.stream()
                .filter(teamId -> existingTeamIds.contains(teamId))
                .toList();


        if (!duplicates.isEmpty()) {
            throw new RuntimeException(
                    "These teams are already linked to tournament " + tournamentId + ": " + duplicates
            );
        }

        tournament.getTeams().addAll(teamsToAdd);
        tournamentRepository.save(tournament);

        return "Teams added successfully";
    }

    @Override
    @Transactional
    public String updateListOfTeamInTournament(Long tournamentId, List<Long> teamIds) {
        Tournament tournament = findTournamentOrThrow(tournamentId);
        List<Team> newTeams = fetchTeamsOrThrow(teamIds);

        // Clear existing links and re-link with new list
        tournament.getTeams().clear();
        tournament.getTeams().addAll(newTeams);

        tournamentRepository.save(tournament);
        return "Tournament teams updated successfully";
    }

    @Override
    @Transactional
    public String deleteTournamentTeamList(Long tournamentId) {
        Tournament tournament = findTournamentOrThrow(tournamentId);

        if (tournament.getTeams().isEmpty()) {
            throw new RuntimeException("No teams linked to tournament " + tournamentId);
        }

        // Only clears the junction table — team entities stay intact
        tournament.getTeams().clear();
        tournamentRepository.save(tournament);
        return "All teams unlinked from tournament successfully";
    }

    private Tournament findTournamentOrThrow(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new TournamentNotFoundException(id));
    }

    private List<Team> fetchTeamsOrThrow(List<Long> teamIds) {
        List<Team> teams = teamRepository.findAllByIds(teamIds);
        if (teams.isEmpty()) {
            throw new TeamNotFoundException(teamIds.get(0)); // first missing ID
        }
        // Check if all requested IDs were found
        if (teams.size() != teamIds.size()) {
            Set<Long> foundIds = teams.stream()
                    .map(Team::getId)
                    .collect(Collectors.toSet());
            List<Long> missingIds = teamIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();
            throw new RuntimeException("Teams not found for IDs: " + missingIds);
        }
        return teams;
    }

    private void validateDates(LocalDate start, LocalDate end) {
        if (start != null && end != null && start.isAfter(end)) {
            throw new RuntimeException(
                    "startDate (" + start + ") cannot be after endDate (" + end + ")"
            );
        }
    }
}
