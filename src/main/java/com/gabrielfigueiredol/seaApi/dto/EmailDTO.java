package com.gabrielfigueiredol.seaApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
    @NotBlank(message = "É necessario informar o email")
    @Email(message = "Email inválido")
    private String email;
}
