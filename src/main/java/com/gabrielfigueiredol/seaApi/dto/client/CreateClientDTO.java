package com.gabrielfigueiredol.seaApi.dto.client;

import com.gabrielfigueiredol.seaApi.dto.EmailDTO;
import com.gabrielfigueiredol.seaApi.dto.PhoneDTO;
import com.gabrielfigueiredol.seaApi.dto.address.AddressRequestDTO;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class CreateClientDTO {
    @NotBlank(message = "É necessário informar o nome")
    @Size(min = 3, max = 100, message = "O nome deve conter entre 3 e 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ0-9 ]+$", message = "Só são permitidos letras, números e espaços")
    private String name;
    @NotBlank(message = "É necessário informar o cpf")
    private String cpf;
    @NotNull(message = "É necessário informar um endereço")
    @Valid
    private AddressRequestDTO address;
    @NotNull(message = "É necessário informar um email")
    @Size(min = 1, message = "É necessário informar no minímo um email")
    @Valid
    private List<EmailDTO> emails;
    @NotNull(message = "É necessário informar um telefone")
    @Size(min = 1, message = "É necessário informar no minímo um telefone")
    @Valid
    private List<PhoneDTO> phones;
}