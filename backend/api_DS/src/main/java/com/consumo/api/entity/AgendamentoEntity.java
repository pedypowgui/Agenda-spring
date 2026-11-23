package com.consumo.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "agendamento")
public class AgendamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer agendamentoId;

    @Column(name = "clienteId", nullable = false)
    private Integer clienteId;

    @Column(name = "profissionalId", nullable = false)
    private Integer profissionalId;

    @Column(name = "horario", nullable = false)
    private LocalTime agendamentoHorario;

    @Column(name = "data", nullable = false)
    private LocalDate agendamentoData;
}
