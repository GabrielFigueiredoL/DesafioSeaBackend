package com.gabrielfigueiredol.seaApi.service;

import com.gabrielfigueiredol.seaApi.dto.EmailDTO;
import com.gabrielfigueiredol.seaApi.model.Client;
import com.gabrielfigueiredol.seaApi.model.Email;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {
    public Email DTOToEntity(EmailDTO dto, Client client) {
        Email email = new Email();
        email.setId(dto.getId());
        email.setEmail(dto.getEmail());
        email.setClient(client);
        return email;
    }

    public EmailDTO entityToDTO(Email email) {
        EmailDTO dto = new EmailDTO();
        dto.setId(email.getId());
        dto.setEmail(email.getEmail());
        return dto;
    }


    public List<Email> DTOToEmailList(List<EmailDTO> emailDTOList, Client client) {
        return emailDTOList.stream()
                .map(dto -> {
                    return DTOToEntity(dto, client);
                })
                .collect(Collectors.toList());
    }


    public List<EmailDTO> emailListToListDTO(List<Email> emails) {
        return emails.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public List<Email> updateEmailList(List<EmailDTO> DTOList, Client client) {
        List<Email> currentEmails = client.getEmails();

        List<Long> ids = DTOList.stream()
                .map(EmailDTO::getId)
                .filter(id -> id != null)
                .collect(Collectors.toList());

        currentEmails.removeIf(email -> email.getId() != null && !ids.contains(email.getId()));

        for (EmailDTO dto : DTOList) {
            if (dto.getId() != null) {
                Email existing = currentEmails.stream()
                        .filter(e -> e.getId().equals(dto.getId()))
                        .findFirst()
                        .orElse(null);

                if (existing != null) {
                    existing.setEmail(dto.getEmail());
                } else {
                    currentEmails.add(DTOToEntity(dto, client));
                }
            } else {
                currentEmails.add(DTOToEntity(dto, client));
            }
        }

        return currentEmails;
    }
}
