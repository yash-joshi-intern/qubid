package com.qubid.backend.dtos.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDto {
    private String phone;
    private String email;
    private String address;
}