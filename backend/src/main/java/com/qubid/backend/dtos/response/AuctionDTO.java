package com.qubid.backend.dtos.response;

import com.qubid.backend.enums.AuctionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionDTO {

    private Long id;

    private String venue;

    private AuctionStatus status;

    private LocalDate eventDate;

    private String title;

    private TournamentDTO tournament;

    private List<AuctionPlayerDTO> auctionPlayers;
}
