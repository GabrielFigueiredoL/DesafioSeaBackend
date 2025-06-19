package com.gabrielfigueiredol.seaApi.service;

import com.gabrielfigueiredol.seaApi.dto.EmailDTO;
import com.gabrielfigueiredol.seaApi.model.Client;
import com.gabrielfigueiredol.seaApi.model.Email;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {
    public List<Email> DTOToEmailList(List<EmailDTO> emailDTOList, Client client) {
        return emailDTOList.stream()
                .map(dto -> {
                    Email email = new Email();
                    email.setEmail(dto.getEmail());
                    email.setClient(client);
                    return email;
                })
                .collect(Collectors.toList());
    }


    public List<String> emailListToString(List<Email> emails) {
        return emails.stream()
                .map(Email::getEmail)
                .collect(Collectors.toList());
    }
}
