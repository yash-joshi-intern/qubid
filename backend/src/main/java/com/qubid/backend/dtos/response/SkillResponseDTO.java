package com.qubid.backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillResponseDTO {
    private Long id;
    private String name;
    private Integer rating;
    private Integer yearsOfExp;
    private String expertiseLevel;
    private Integer playersCount;
}
