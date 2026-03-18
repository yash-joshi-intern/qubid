package com.qubid.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(name = "base_price")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasePrice extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "player_id")
    Player player;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    Tournament tournament;

    BigInteger basePrice;

}
