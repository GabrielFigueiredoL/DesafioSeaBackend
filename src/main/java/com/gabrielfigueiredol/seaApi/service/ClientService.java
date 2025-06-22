package com.gabrielfigueiredol.seaApi.service;

import com.gabrielfigueiredol.seaApi.dto.client.ClientResponseDTO;
import com.gabrielfigueiredol.seaApi.dto.client.CreateClientDTO;
import com.gabrielfigueiredol.seaApi.dto.client.UpdateClientDTO;
import com.gabrielfigueiredol.seaApi.model.Address;
import com.gabrielfigueiredol.seaApi.model.Client;
import com.gabrielfigueiredol.seaApi.model.Email;
import com.gabrielfigueiredol.seaApi.model.Phone;
import com.gabrielfigueiredol.seaApi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setCpf(formatCpf(client.getCpf()));
        dto.setAddress(addressService.entityToResponseDTO(client.getAddress()));
        dto.setPhones(phoneService.entityListToResponseListDTO(client.getPhones()));
        dto.setEmails(emailService.emailListToListDTO(client.getEmails()));
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

    public Optional<Client> getById(Long id) {
        return clientRepository.findById(id);
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    public void update(Client client, UpdateClientDTO updateClientDTO) {
        client.setName(updateClientDTO.getName());
        client.setCpf(updateClientDTO.getCpf());

        addressService.updateAddressEntity(client.getAddress(), updateClientDTO.getAddress());

        List<Phone> phoneList = phoneService.updatePhoneList(updateClientDTO.getPhones(), client);
        client.setPhones(phoneList);

        List<Email> emailList = emailService.updateEmailList(updateClientDTO.getEmails(), client);
        client.setEmails(emailList);

        clientRepository.save(client);
    }
}
