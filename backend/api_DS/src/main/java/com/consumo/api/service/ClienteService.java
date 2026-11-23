package com.consumo.api.service;

import com.consumo.api.entity.ClienteEntity;
import com.consumo.api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;

    // Listar todos
    @Transactional(readOnly = true)
    public List<ClienteEntity> buscar() {
        return repository.findAll();
    }

    // Buscar por id
    @Transactional(readOnly = true)
    public ClienteEntity buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado: " + id)
        );
    }

    // Cadastro de usuário
    @Transactional
    public ClienteEntity salvar(ClienteEntity cliente) {
        cliente.setClienteId(null);
        cliente.setClienteEmail(cliente.getClienteEmail());
        cliente.setClienteNome(cliente.getClienteNome());
        cliente.setClienteCpf(cliente.getClienteCpf());
        cliente.setClienteTelefone(cliente.getClienteTelefone());
        return repository.saveAndFlush(cliente);
    }

    // Atualizar usuário
    @Transactional
    public ClienteEntity atualizar (Integer id, ClienteEntity novosDados) {
        ClienteEntity atual = buscarPorId(id);

        atual.setClienteNome(novosDados.getClienteNome());
        atual.setClienteEmail(novosDados.getClienteEmail());
        atual.setClienteTelefone(novosDados.getClienteTelefone());
        atual.setClienteCpf(novosDados.getClienteCpf());

        return repository.saveAndFlush(atual);
    }

    // Deletar usuário
    @Transactional
    public void excluir(Integer id) {
        ClienteEntity cliente = buscarPorId(id);
        repository.delete(cliente);
    }
}
