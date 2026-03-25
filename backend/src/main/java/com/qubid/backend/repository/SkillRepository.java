package com.qubid.backend.repository;

import com.qubid.backend.dtos.response.SkillPlayerRowDTO;
import com.qubid.backend.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    boolean existsByNameIgnoreCase(String name);

    Optional<Skill> findByNameIgnoreCase(String name);

    List<Skill> findAllByExpertiseLevelIgnoreCase(String expertiseLevel);

    List<Skill> findAllByRatingLessThanEqual(Integer rating);

    List<Skill> findByNameContainingIgnoreCase(String namePart);

    @Query("""
                select new com.qubid.backend.dtos.response.SkillPlayerRowDTO(
                    s.id,
                    p
                ) 
                from Skill s 
                left join s.players p 
                where s.id = :skillId
            """)
    List<SkillPlayerRowDTO> findPlayerRowsBySkillId(@Param("skillId") Long skillId);

}
