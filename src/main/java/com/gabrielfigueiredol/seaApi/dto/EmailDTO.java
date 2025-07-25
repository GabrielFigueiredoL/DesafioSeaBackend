package com.gabrielfigueiredol.seaApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
    private Long id;
    @NotBlank(message = "É necessario informar o email")
    @Email(message = "Email inválido")
    private String email;
}
