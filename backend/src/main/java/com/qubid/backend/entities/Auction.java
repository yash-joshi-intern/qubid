package com.qubid.backend.entities;

import com.qubid.backend.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auctions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auction extends BaseEntity{

    private String venue;

    private Status status;

    private LocalDate eventDate;

    private String Title;

    @ManyToMany(mappedBy = "auctionList")
    private List<AuctionPlayer> auctionPlayerList = new ArrayList<>();


}
