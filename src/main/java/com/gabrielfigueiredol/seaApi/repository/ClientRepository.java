package com.gabrielfigueiredol.seaApi.repository;


import com.gabrielfigueiredol.seaApi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
