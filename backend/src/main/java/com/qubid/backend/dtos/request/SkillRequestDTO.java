package com.qubid.backend.dtos.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillRequestDto {
    private String name;
    private Integer rating;
    private Integer yearsOfExp;
    private String expertiseLevel;
}
