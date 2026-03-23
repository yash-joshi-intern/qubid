package com.qubid.backend.repository;

import com.qubid.backend.entities.Franchise;
import org.springframework.data.jpa.repository.EntityGraph;
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

    @Query("select f from Franchise f where lower(f.name) like lower(concat('%', :namePart, '%'))")
    List<Franchise> searchByName(@Param("namePart") String namePart);

    // Fetch graph eagerly for detail use-cases to reduce additional lazy loads.
    @EntityGraph(attributePaths = {"teamList", "tournamentList"})
    @Query("select f from Franchise f where f.id = :id")
    Optional<Franchise> findDetailedById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"teamList", "tournamentList"})
    @Query("select distinct f from Franchise f")
    List<Franchise> findAllDetailed();
}
