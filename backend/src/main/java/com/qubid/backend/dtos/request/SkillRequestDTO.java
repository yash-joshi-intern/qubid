package com.qubid.backend.dtos.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillRequestDTO {
    @NotBlank(message = "Skill name is required")
    @Size(max = 100, message = "Skill name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    private Integer rating;

    @NotNull(message = "Years of experience is required")
    @Min(value = 0, message = "Years of experience cannot be negative")
    @Max(value = 50, message = "Years of experience seems unrealistic")
    private Integer yearsOfExp;

    @NotBlank(message = "Expertise level is required")
    @Pattern(
            regexp = "BEGINNER|INTERMEDIATE|ADVANCED|EXPERT",
            message = "Expertise level must be one of: BEGINNER, INTERMEDIATE, ADVANCED, EXPERT"
    )
    private String expertiseLevel;
}
