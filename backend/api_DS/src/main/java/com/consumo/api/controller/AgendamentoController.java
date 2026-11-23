package com.consumo.api.controller;

import com.consumo.api.entity.AgendamentoEntity;
import com.consumo.api.repository.AgendamentoRepository;
import com.consumo.api.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamento")
@RequiredArgsConstructor
public class AgendamentoController {
    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoEntity> salvar(@Valid @RequestBody AgendamentoEntity agendamento){
        AgendamentoEntity salvo = agendamentoService.salvar(agendamento);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public List<AgendamentoEntity> buscarTodos() {
        return agendamentoService.buscar();
    }

    @GetMapping("/{id}")
    public AgendamentoEntity buscarPotId(@PathVariable Integer id) {
        return agendamentoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public AgendamentoEntity atualizar(@PathVariable Integer id, @Valid @RequestBody AgendamentoEntity agendamento){
        return agendamentoService.atualizar(id, agendamento);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id){
        agendamentoService.excluir(id);
    }
}