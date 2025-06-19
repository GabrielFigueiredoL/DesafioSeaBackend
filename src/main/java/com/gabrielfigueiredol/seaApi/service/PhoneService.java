package com.gabrielfigueiredol.seaApi.service;

import com.gabrielfigueiredol.seaApi.dto.PhoneDTO;
import com.gabrielfigueiredol.seaApi.model.Client;
import com.gabrielfigueiredol.seaApi.model.Phone;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneService {
    List<Phone> DTOToPhoneList(List<PhoneDTO> phoneDTOList, Client client) {
        return phoneDTOList.stream()
                .map(phoneDTO -> {
                    Phone phone = new Phone(phoneDTO.getType(), phoneDTO.getNumber());
                    cleanPhone(phone);
                    phone.setClient(client);
                    return phone;
                })
                .collect(Collectors.toList());
    }

    public List<PhoneDTO> entityListToResponseListDTO(List<Phone> phones) {
        return phones.stream()
                .map(this::entityToResponseDTO)
                .collect(Collectors.toList());
    }

    public PhoneDTO entityToResponseDTO(Phone phone) {
        return new PhoneDTO(phone.getType().getLabel(), formatPhone(phone.getNumber()));
    }

    public void cleanPhone(Phone phone) {
        String formatedPhone = phone.getNumber().replaceAll("[^0-9]", "");
        if (phone.getType() == Phone.PhoneType.CELULAR) {
            if (formatedPhone.length() != 11) {
                throw new IllegalArgumentException("O telefone celular deve conter 11 digitos, sendo dois digitos para o DDD, um digito 9 e oito digitos para o numero de telefone");
            }
        } else if (formatedPhone.length() != 10) {
            throw new IllegalArgumentException("O telefone deve conter 10 digitos");
        }
        phone.setNumber(formatedPhone);
    }

    public String formatPhone(String phone) {
        if (phone.length() == 11) {
            return String.format("(%s) %s-%s",
                    phone.substring(0, 2),
                    phone.substring(2, 7),
                    phone.substring(7, 11));
        } else {
            return String.format("(%s) %s-%s",
                    phone.substring(0, 2),
                    phone.substring(2, 6),
                    phone.substring(6, 10));
        }
    }
}

