package com.gabrielfigueiredol.seaApi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "telefones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tipo")
    private PhoneType type;
    @Column(name = "numero")
    private String number;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Client client;

    @Getter
    public enum PhoneType {
        RESIDENCIAL("residencial"),
        COMERCIAL("comercial"),
        CELULAR("celular");

        private final String label;

        PhoneType(String label) {
            this.label = label;
        }
    }

    public Phone(String type, String number) {
        this.type = PhoneType.valueOf(type.toUpperCase());
        this.number = number;
    }
}
