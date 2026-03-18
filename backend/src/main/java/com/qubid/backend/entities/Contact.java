package com.qubid.backend.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {
    private String phone;
    private String email;
    private String address;
}