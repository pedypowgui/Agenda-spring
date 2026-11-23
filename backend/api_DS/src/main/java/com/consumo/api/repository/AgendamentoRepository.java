package com.consumo.api.repository;

import com.consumo.api.entity.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Integer> {
}
