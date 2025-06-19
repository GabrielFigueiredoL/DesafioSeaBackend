package com.gabrielfigueiredol.seaApi.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDTO {
    private String formatedCep;
    private String street;
    private String district;
    private String city;
    private String stateCode;
    private String complement;
}