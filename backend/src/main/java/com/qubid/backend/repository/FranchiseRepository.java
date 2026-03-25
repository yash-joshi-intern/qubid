package com.qubid.backend.repository;

import com.qubid.backend.dtos.response.FranchiseDTO;
import com.qubid.backend.dtos.response.FranchiseTeamRowDTO;
import com.qubid.backend.dtos.response.FranchiseTournamentRowDTO;
import com.qubid.backend.entities.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FranchiseRepository extends JpaRepository<Franchise, Long> {

    // Get Franchise By its name
    boolean existsFranchiseByName(String name);

    // Case-insensitive checks and lookups for cleaner validation/search.
    boolean existsFranchiseByNameIgnoreCase(String name);

    Optional<Franchise> findByNameIgnoreCase(String name);

    List<Franchise> findAllByCountryIgnoreCase(String country);

    List<Franchise> findByNameContainingIgnoreCase(String namePart);

    @Query("""
            select new com.qubid.backend.dtos.response.FranchiseDTO(
                f.id,
                f.name,
                f.city,
                f.country,
                new com.qubid.backend.dtos.response.ContactDTO(
                    f.contact.phone,
                    f.contact.email,
                    f.contact.address
                )
            )
            from Franchise f
            where f.id = :id
            """)
    Optional<FranchiseDTO> findFranchiseDtoById(@Param("id") Long id);

    @Query("""
            select new com.qubid.backend.dtos.response.FranchiseDTO(
                f.id,
                f.name,
                f.city,
                f.country,
                new com.qubid.backend.dtos.response.ContactDTO(
                    f.contact.phone,
                    f.contact.email,
                    f.contact.address
                )
            )
            from Franchise f
            """)
    List<FranchiseDTO> findAllFranchiseDtos();

    @Query("""
            select new com.qubid.backend.dtos.response.FranchiseTeamRowDTO(
                f.id,
                t
            )
            from Franchise f
            left join f.teams t
            where f.id = :franchiseId
            """)
    List<FranchiseTeamRowDTO> findTeamRowsByFranchiseId(@Param("franchiseId") Long franchiseId);

    @Query("""
            select new com.qubid.backend.dtos.response.FranchiseTournamentRowDTO(
                f.id,
                tr
            )
            from Franchise f
            left join f.tournaments tr
            where f.id = :franchiseId
            """)
    List<FranchiseTournamentRowDTO> findTournamentRowsByFranchiseId(@Param("franchiseId") Long franchiseId);

    @Query("""
            select new com.qubid.backend.dtos.response.FranchiseTeamRowDTO(
                f.id,
                t
            )
            from Franchise f
            left join f.teams t
            where f.id in :franchiseIds
            """)
    List<FranchiseTeamRowDTO> findTeamRowsByFranchiseIds(@Param("franchiseIds") List<Long> franchiseIds);

    @Query("""
            select new com.qubid.backend.dtos.response.FranchiseTournamentRowDTO(
                f.id,
                tr
            )
            from Franchise f
            left join f.tournaments tr
            where f.id in :franchiseIds
            """)
    List<FranchiseTournamentRowDTO> findTournamentRowsByFranchiseIds(@Param("franchiseIds") List<Long> franchiseIds);
}
