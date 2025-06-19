package com.gabrielfigueiredol.seaApi.service;

import com.gabrielfigueiredol.seaApi.dto.client.ClientResponseDTO;
import com.gabrielfigueiredol.seaApi.dto.client.CreateClientDTO;
import com.gabrielfigueiredol.seaApi.model.Client;
import com.gabrielfigueiredol.seaApi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PhoneService phoneService;

    @Autowired
    EmailService emailService;

    @Autowired
    AddressService addressService;

    @Transactional
    public Client create(CreateClientDTO clientDTO) {
        return clientRepository.save(dtoToEntity(clientDTO));
    }


    public List<ClientResponseDTO> getAll() {
        return clientRepository.findAll().stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public Client dtoToEntity(CreateClientDTO clientDTO) {
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setCpf(cleanCpf(clientDTO.getCpf()));
        client.setAddress(addressService.requestToEntity(clientDTO.getAddress()));
        client.setPhones(phoneService.DTOToPhoneList(clientDTO.getPhones(), client));
        client.setEmails(emailService.DTOToEmailList(clientDTO.getEmails(), client));
        return client;
    }

    public ClientResponseDTO entityToDTO(Client client) {
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setName(client.getName());
        dto.setCpf(formatCpf(client.getCpf()));
        dto.setAddress(addressService.entityToResponseDTO(client.getAddress()));
        dto.setPhones(phoneService.entityListToResponseListDTO(client.getPhones()));
        dto.setEmails(emailService.emailListToString(client.getEmails()));
        return dto;
    }

    public String formatCpf(String cpf) {
        return cpf.substring(0, 3) + "." +
                cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" +
                cpf.substring(9, 11);
    }

    public String cleanCpf(String cpf) {
        String formatedCpf = cpf.replaceAll("[^0-9]", "");
        if (formatedCpf.length() != 11) {
            throw new IllegalArgumentException("cpf inválido");
        }
        return formatedCpf;
    }
}
