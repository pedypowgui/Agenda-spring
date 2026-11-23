package com.consumo.api.repository;

import com.consumo.api.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
// Permite usar diversas funcoes prontas como, save(cliente), findAll(), FindById(id), etc.
}
