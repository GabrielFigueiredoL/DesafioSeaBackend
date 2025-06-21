package com.gabrielfigueiredol.seaApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PhoneDTO {
    @NotBlank(message = "É necessário informar o tipo de telefone")
    private String type;
    @NotBlank(message = "É necessário informar o tipo número do telefone")
    private String number;
}
