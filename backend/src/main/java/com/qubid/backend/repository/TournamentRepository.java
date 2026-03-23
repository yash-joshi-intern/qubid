package com.qubid.backend.repository;


import com.qubid.backend.entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    @Query("SELECT t FROM Tournament t WHERE t.id IN :ids")
    List<Tournament> findAllByIds(@Param("ids") List<Long> ids);
}
