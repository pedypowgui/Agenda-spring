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
@Table(name = "profissional")
public class ProfissionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profissionalId;

    @NotBlank(message = "O nome é obrigatório")
    @Column(name = "nome", nullable = false)
    String profissionalNome;

    @NotBlank(message = "Especialidade é obrigatório")
    @Column(name = "especialidade", nullable = false)
    private String profissionalEspecialidade;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(name = "telefone", nullable = false)
    private String profissionalTelefone;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Informe um email válido")
    @Column(name = "email", unique = true, nullable = false)
    private String profissionalEmail;
}