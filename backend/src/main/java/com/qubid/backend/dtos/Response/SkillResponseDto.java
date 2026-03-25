package com.qubid.backend.dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillResponseDto {
    private Long id;
    private String name;
    private Integer rating;
    private Integer yearsOfExp;
    private String expertiseLevel;
    private Integer playersCount;
}
