package com.qubid.backend.dtos.Request;

import com.qubid.backend.enums.AuctionStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionRequestDTO {
    @NotBlank(message = "Venue cannot be blank")
    @Size(min = 3, max = 100, message = "Venue must be between 3 and 100 characters")
    private String venue;

    private AuctionStatus status = AuctionStatus.NOT_STARTED;

    @NotNull(message = "Event date cannot be null")
    @FutureOrPresent(message = "Event date cannot be in the past")
    private LocalDate eventDate;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotNull(message = "Tournament ID cannot be null")
    @Positive(message = "Tournament ID must be a positive number")
    private Long tournamentId;
}
