package com.qubid.backend.dtos.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillResponseDTO {
    private Long id;
    private String name;
    private Integer rating;
    private Integer yearsOfExp;
    private String expertiseLevel;
}