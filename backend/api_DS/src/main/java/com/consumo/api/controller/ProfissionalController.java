package com.consumo.api.controller;

import com.consumo.api.entity.ProfissionalEntity;
import com.consumo.api.service.ProfissionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissional")
@RequiredArgsConstructor
public class ProfissionalController {
    private final ProfissionalService profissionalService;

    @PostMapping
    public ResponseEntity<ProfissionalEntity> salvar(@Valid @RequestBody ProfissionalEntity profissional) {
        ProfissionalEntity salvo = profissionalService.salvar(profissional);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public List<ProfissionalEntity> buscarTodos() {
        return profissionalService.buscar();
    }

    @GetMapping("/{id}")
    public ProfissionalEntity buscarPorId(@PathVariable Integer id) {
        return profissionalService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ProfissionalEntity atualizar(@PathVariable Integer id, @Valid @RequestBody ProfissionalEntity profissional) {
        return profissionalService.atualizar(id, profissional);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletar(@PathVariable Integer id) {
        profissionalService.excluir(id);
    }
}
