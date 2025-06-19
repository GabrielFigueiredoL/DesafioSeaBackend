package com.gabrielfigueiredol.seaApi.controller;

import com.gabrielfigueiredol.seaApi.dto.client.ClientResponseDTO;
import com.gabrielfigueiredol.seaApi.dto.client.CreateClientDTO;
import com.gabrielfigueiredol.seaApi.model.Client;
import com.gabrielfigueiredol.seaApi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("clients")
public class ClientController {
    @Autowired
    ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@RequestBody @Valid CreateClientDTO clientPostDTO) {
        Client client = clientService.create(clientPostDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(client.getId())
                .toUri();


        return ResponseEntity.created(uri).body(clientService.entityToDTO(client));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAll() {
        return ResponseEntity.ok().body(clientService.getAll());
    }
}
