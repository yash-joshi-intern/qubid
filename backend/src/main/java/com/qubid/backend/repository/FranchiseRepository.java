package com.qubid.backend.repository;

import com.qubid.backend.dtos.Response.FranchiseDto;
import com.qubid.backend.dtos.Response.FranchiseTeamRowDto;
import com.qubid.backend.dtos.Response.FranchiseTournamentRowDto;
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
            select new com.qubid.backend.dtos.Response.FranchiseDto(
                f.id,
                f.name,
                f.city,
                f.country,
                new com.qubid.backend.dtos.Response.ContactDto(
                    f.contact.phone,
                    f.contact.email,
                    f.contact.address
                )
            )
            from Franchise f
            where f.id = :id
            """)
    Optional<FranchiseDto> findFranchiseDtoById(@Param("id") Long id);

    @Query("""
            select new com.qubid.backend.dtos.Response.FranchiseDto(
                f.id,
                f.name,
                f.city,
                f.country,
                new com.qubid.backend.dtos.Response.ContactDto(
                    f.contact.phone,
                    f.contact.email,
                    f.contact.address
                )
            )
            from Franchise f
            """)
    List<FranchiseDto> findAllFranchiseDtos();

    @Query("""
            select new com.qubid.backend.dtos.Response.FranchiseTeamRowDto(
                f.id,
                t
            )
            from Franchise f
            left join f.teams t
            where f.id = :franchiseId
            """)
    List<FranchiseTeamRowDto> findTeamRowsByFranchiseId(@Param("franchiseId") Long franchiseId);

    @Query("""
            select new com.qubid.backend.dtos.Response.FranchiseTournamentRowDto(
                f.id,
                tr
            )
            from Franchise f
            left join f.tournaments tr
            where f.id = :franchiseId
            """)
    List<FranchiseTournamentRowDto> findTournamentRowsByFranchiseId(@Param("franchiseId") Long franchiseId);

    @Query("""
            select new com.qubid.backend.dtos.Response.FranchiseTeamRowDto(
                f.id,
                t
            )
            from Franchise f
            left join f.teams t
            where f.id in :franchiseIds
            """)
    List<FranchiseTeamRowDto> findTeamRowsByFranchiseIds(@Param("franchiseIds") List<Long> franchiseIds);

    @Query("""
            select new com.qubid.backend.dtos.Response.FranchiseTournamentRowDto(
                f.id,
                tr
            )
            from Franchise f
            left join f.tournaments tr
            where f.id in :franchiseIds
            """)
    List<FranchiseTournamentRowDto> findTournamentRowsByFranchiseIds(@Param("franchiseIds") List<Long> franchiseIds);
}
