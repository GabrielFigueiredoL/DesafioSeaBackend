package com.gabrielfigueiredol.seaApi.service;

import com.gabrielfigueiredol.seaApi.dto.PhoneDTO;
import com.gabrielfigueiredol.seaApi.model.Client;
import com.gabrielfigueiredol.seaApi.model.Phone;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneService {

    public Phone DTOToEntity(PhoneDTO dto, Client client) {
        Phone phone = new Phone();
        phone.setId(dto.getId());
        phone.setType(Phone.PhoneType.fromLabel(dto.getType()));
        phone.setNumber(cleanPhone(dto.getNumber()));
        phone.setClient(client);
        return phone;
    }

    public PhoneDTO entityToDTO(Phone phone) {
        return new PhoneDTO(
                phone.getId(),
                phone.getType().getLabel(),
                formatPhone(phone.getNumber())
        );
    }

    public List<Phone> DTOToPhoneList(List<PhoneDTO> phoneDTOList, Client client) {
        return phoneDTOList.stream()
                .map(dto -> DTOToEntity(dto, client))
                .collect(Collectors.toList());
    }

    public List<PhoneDTO> entityListToResponseListDTO(List<Phone> phones) {
        return phones.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public List<Phone> updatePhoneList(List<PhoneDTO> dtoList, Client client) {
        List<Phone> currentPhones = client.getPhones();

        List<Long> ids = dtoList.stream()
                .map(PhoneDTO::getId)
                .filter(id -> id != null)
                .collect(Collectors.toList());

        currentPhones.removeIf(phone -> phone.getId() != null && !ids.contains(phone.getId()));

        for (PhoneDTO dto : dtoList) {
            if (dto.getId() != null) {
                Phone existing = currentPhones.stream()
                        .filter(p -> p.getId().equals(dto.getId()))
                        .findFirst()
                        .orElse(null);

                if (existing != null) {
                    existing.setType(Phone.PhoneType.fromLabel(dto.getType()));
                    existing.setNumber(cleanPhone(dto.getNumber()));
                } else {
                    currentPhones.add(DTOToEntity(dto, client));
                }
            } else {
                currentPhones.add(DTOToEntity(dto, client));
            }
        }

        return currentPhones;
    }

    public String cleanPhone(String phone) {
        String cleaned = phone.replaceAll("[^0-9]", "");

        if (cleaned.length() == 11) {
            return cleaned;
        } else if (cleaned.length() == 10) {
            return cleaned;
        } else {
            throw new IllegalArgumentException("Número de telefone inválido. Deve conter 10 (fixo) ou 11 (celular) dígitos.");
        }
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
