package com.gabrielfigueiredol.seaApi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String name;
    private String cpf;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Address address;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Email> emails = new ArrayList<>();
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Phone> phones = new ArrayList<>();
}
