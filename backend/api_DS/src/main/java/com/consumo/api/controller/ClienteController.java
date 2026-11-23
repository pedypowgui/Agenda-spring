package com.consumo.api.controller;

import com.consumo.api.entity.ClienteEntity;
import com.consumo.api.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteEntity> salvar(@Valid @RequestBody ClienteEntity cliente) {
        ClienteEntity salvo = clienteService.salvar(cliente);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public List<ClienteEntity> buscarTodos() {
        return clienteService.buscar();
    }

    @GetMapping("/{id}")
    public ClienteEntity buscarPorId(@PathVariable Integer id) {
        return clienteService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ClienteEntity atualizar(@PathVariable Integer id, @Valid @RequestBody ClienteEntity cliente){
        return clienteService.atualizar(id, cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletar(@PathVariable Integer id){
        clienteService.excluir(id);
    }
}
