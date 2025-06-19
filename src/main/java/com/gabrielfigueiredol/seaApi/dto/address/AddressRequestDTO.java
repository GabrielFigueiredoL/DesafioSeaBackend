package com.gabrielfigueiredol.seaApi.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDTO {
    @NotBlank(message = "É necessário informar o cep")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter exatamente 8 dígitos numéricos")
    private String cep;
    @NotBlank(message = "É necessário informar o logradouro")
    private String street;
    @NotBlank(message = "É necessário informar o bairro")
    private String district;
    @NotBlank(message = "É necessário informar a cidade")
    private String city;
    @NotBlank(message = "É necessário informar a UF.")
    private String stateCode;
    private String complement;
}
