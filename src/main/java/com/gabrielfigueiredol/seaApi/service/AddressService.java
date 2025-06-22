package com.gabrielfigueiredol.seaApi.service;

import com.gabrielfigueiredol.seaApi.dto.address.AddressRequestDTO;
import com.gabrielfigueiredol.seaApi.dto.address.AddressResponseDTO;
import com.gabrielfigueiredol.seaApi.model.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    public Address requestToEntity(AddressRequestDTO addressDTO) {
        Address address = new Address();
        address.setCep(cleanCep(addressDTO.getCep()));
        address.setStreet(addressDTO.getStreet());
        address.setDistrict(addressDTO.getDistrict());
        address.setCity(addressDTO.getCity());
        address.setStateCode(addressDTO.getStateCode());
        address.setComplement(addressDTO.getComplement());
        return address;
    }

    AddressResponseDTO entityToResponseDTO (Address address) {
        AddressResponseDTO dto = new AddressResponseDTO();
        String formatedCep = formatCep(address.getCep());
        dto.setCep(formatedCep);
        dto.setStreet(address.getStreet());
        dto.setDistrict(address.getDistrict());
        dto.setCity(address.getCity());
        dto.setStateCode(address.getStateCode());
        dto.setComplement(address.getComplement());

        return dto;
    }

    String formatCep(String cep) {
        return cep.substring(0, 5) + "-" + cep.substring(5);
    }

    String cleanCep(String cep) {
        String formatedCep = cep.replaceAll("[^0-9]", "");
        if (formatedCep.length() != 8) {
            throw new IllegalArgumentException("Cep inválido");
        }
        return formatedCep;
    }
}

