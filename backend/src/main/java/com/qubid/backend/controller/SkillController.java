package com.qubid.backend.controller;

import com.qubid.backend.Response.ApiResponse;
import com.qubid.backend.dtos.request.SkillRequestDTO;
import com.qubid.backend.dtos.response.SkillDTO;
import com.qubid.backend.dtos.response.SkillPlayerDTO;
import com.qubid.backend.dtos.response.SkillResponseDTO;
import com.qubid.backend.services.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skill")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<SkillResponseDTO>> addSkill(@RequestBody @Valid SkillRequestDTO skillRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(skillService.createSkill(skillRequestDto), "Skill Created"));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<SkillResponseDTO>> updateSkill(@RequestParam Long id, @RequestBody @Valid SkillRequestDTO skillRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(skillService.updateSkill(id, skillRequestDto), "Skill Updated"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteSkill(@RequestParam Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SkillDTO>>> getAllSkills() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(skillService.getAllSkills(), "Skills List"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillDTO>> getSkillById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(skillService.getSkillById(id), "Skill Fetched"));
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<ApiResponse<SkillDTO>> getSkillByName(@RequestParam String name) {
        SkillDTO skillDto = skillService.getSkillByName(name)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(skillDto, "Skill Fetched"));
    }

    @GetMapping("/search-by-name-like")
    public ResponseEntity<ApiResponse<List<SkillDTO>>> searchSkillsByName(@RequestParam String namePart) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(skillService.searchSkillsByName(namePart), "Skill Search Result"));
    }

    @GetMapping("/search-by-expertise-level")
    public ResponseEntity<ApiResponse<List<SkillDTO>>> getSkillsByExpertiseLevel(@RequestParam String expertiseLevel) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(skillService.getSkillsByExpertiseLevel(expertiseLevel), "Skills By Expertise Level"));
    }

    @GetMapping("/search-by-max-rating")
    public ResponseEntity<ApiResponse<List<SkillDTO>>> getSkillsByMaximumRating(@RequestParam Integer rating) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(skillService.getSkillByMaximumRating(rating), "Skills By Maximum Rating"));
    }

    @GetMapping("/search-players-by-skill")
    public ResponseEntity<ApiResponse<SkillPlayerDTO>> getPlayersBySkill(@RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(skillService.getPlayersBySkillId(id), "Players By Skills Id"));
    }
}
