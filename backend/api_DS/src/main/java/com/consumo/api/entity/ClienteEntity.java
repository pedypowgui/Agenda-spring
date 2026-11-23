package com.consumo.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clienteId;

    @NotBlank(message = "O nome é obrigatório")
    @Column(name = "nome", nullable = false)
    private String clienteNome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Informe um email válido")
    @Column(name = "email", unique = true, nullable = false)
    private String clienteEmail;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(name = "telefone", nullable = false)
    private String clienteTelefone;

    @NotBlank(message = "CPF é obrigatório")
    @Column(name = "cpf", unique = true, nullable = false)
    private String clienteCpf;
}
