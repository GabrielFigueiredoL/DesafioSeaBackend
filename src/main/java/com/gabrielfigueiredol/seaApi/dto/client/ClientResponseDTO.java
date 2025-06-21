package com.gabrielfigueiredol.seaApi.dto.client;

import com.gabrielfigueiredol.seaApi.dto.PhoneDTO;
import com.gabrielfigueiredol.seaApi.dto.address.AddressResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDTO {
    private Long id;
    private String name;
    private String cpf;
    private List<String> emails;
    private List<PhoneDTO> phones;
    private AddressResponseDTO address;
}
