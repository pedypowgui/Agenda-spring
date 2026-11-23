package com.consumo.api.service;

import com.consumo.api.entity.AgendamentoEntity;
import com.consumo.api.entity.ProfissionalEntity;
import com.consumo.api.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {
    private final AgendamentoRepository repository;

    @Transactional(readOnly = true)
    public List<AgendamentoEntity> buscar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public AgendamentoEntity buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado: " + id)
        );
    }

    @Transactional
    public AgendamentoEntity salvar(AgendamentoEntity agendamento) {
        agendamento.setAgendamentoId(null);
        agendamento.setClienteId(agendamento.getClienteId());
        agendamento.setProfissionalId(agendamento.getProfissionalId());
        agendamento.setAgendamentoData(agendamento.getAgendamentoData());
        agendamento.setAgendamentoHorario(agendamento.getAgendamentoHorario());
        return repository.saveAndFlush(agendamento);
    }

    @Transactional
    public AgendamentoEntity atualizar(Integer id, AgendamentoEntity novosDados) {
        AgendamentoEntity atual = buscarPorId(id);

        atual.setClienteId(novosDados.getClienteId());
        atual.setProfissionalId(novosDados.getProfissionalId());
        atual.setAgendamentoHorario(novosDados.getAgendamentoHorario());
        atual.setAgendamentoData(novosDados.getAgendamentoData());

        return repository.saveAndFlush(atual);
    }

    @Transactional
    public void excluir(Integer id){
        AgendamentoEntity agendamento = buscarPorId(id);
        repository.delete(agendamento);
    }
}
