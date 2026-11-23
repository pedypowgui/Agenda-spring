package com.consumo.api.service;

import com.consumo.api.entity.ClienteEntity;
import com.consumo.api.entity.ProfissionalEntity;
import com.consumo.api.repository.ProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfissionalService {
    private final ProfissionalRepository repository;

    @Transactional(readOnly = true)
    public List<ProfissionalEntity> buscar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public ProfissionalEntity buscarPorId(Integer id){
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado: " + id)
        );
    }

    @Transactional
    public ProfissionalEntity salvar(ProfissionalEntity profissional){
        profissional.setProfissionalId(null);
        profissional.setProfissionalEmail(profissional.getProfissionalEmail());
        profissional.setProfissionalNome(profissional.getProfissionalNome());
        profissional.setProfissionalEspecialidade(profissional.getProfissionalEspecialidade());
        profissional.setProfissionalTelefone(profissional.getProfissionalTelefone());

        return repository.saveAndFlush(profissional);
    }

    @Transactional
    public ProfissionalEntity atualizar (Integer id, ProfissionalEntity novosDados){
        ProfissionalEntity atual = buscarPorId(id);

        atual.setProfissionalEmail(novosDados.getProfissionalEmail());
        atual.setProfissionalNome(novosDados.getProfissionalNome());
        atual.setProfissionalEspecialidade(novosDados.getProfissionalEspecialidade());
        atual.setProfissionalTelefone(novosDados.getProfissionalTelefone());

        return repository.saveAndFlush(atual);
    }

    @Transactional
    public void excluir(Integer id){
        ProfissionalEntity profissional = buscarPorId(id);
        repository.delete(profissional);
    }
}
