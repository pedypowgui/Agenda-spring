package com.consumo.api.controller;

import com.consumo.api.entity.AgendamentoEntity;
import com.consumo.api.entity.ClienteEntity;
import com.consumo.api.entity.ProfissionalEntity;
import com.consumo.api.service.AgendamentoService;
import com.consumo.api.service.ClienteService;
import com.consumo.api.service.ProfissionalService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AgendamentoPaginaController {

    private final AgendamentoService agendamentoService;
    private final ClienteService clienteService;
    private final ProfissionalService profissionalService;

    public AgendamentoPaginaController(AgendamentoService agendamentoService,
                                       ClienteService clienteService,
                                       ProfissionalService profissionalService) {
        this.agendamentoService = agendamentoService;
        this.clienteService = clienteService;
        this.profissionalService = profissionalService;
    }

    @GetMapping("/")
    public String listar(Model model) {
        var agendamentos = agendamentoService.buscar();
        var clientes = clienteService.buscar();
        var profissionais = profissionalService.buscar();
        Map<Integer, String> clienteNomes = new HashMap<>();
        Map<Integer, String> profissionalNomes = new HashMap<>();

        for (ClienteEntity cliente : clientes) {
            clienteNomes.put(cliente.getClienteId(), cliente.getClienteNome());
        }
        for (ProfissionalEntity profissional : profissionais) {
            profissionalNomes.put(profissional.getProfissionalId(), profissional.getProfissionalNome());
        }

        model.addAttribute("agendamentos", agendamentos);
        model.addAttribute("clienteNomes", clienteNomes);
        model.addAttribute("profissionalNomes", profissionalNomes);
        return "index";
    }

    @GetMapping("/agendamentos/novo")
    public String novo(Model model) {
        model.addAttribute("agendamento", new AgendamentoEntity());
        model.addAttribute("edicao", false);
        carregarOpcoes(model);
        return "agendamento-form";
    }

    @GetMapping("/agendamentos/editar/{id}")
    public String editar(@PathVariable Integer id,
                         Model model,
                         RedirectAttributes redirect) {
        try {
            model.addAttribute("agendamento", agendamentoService.buscarPorId(id));
            model.addAttribute("edicao", true);
            carregarOpcoes(model);
            return "agendamento-form";
        } catch (ResponseStatusException ex) {
            redirect.addFlashAttribute("erro", "Agendamento não encontrado.");
            return "redirect:/";
        }
    }

    @PostMapping("/agendamentos")
    public String salvar(@ModelAttribute("agendamento") AgendamentoEntity agendamento,
                         Model model,
                         RedirectAttributes redirect) {
        String erro = validarCampos(agendamento);
        if (erro == null) {
            erro = validarReferencias(agendamento);
        }
        if (erro != null) {
            model.addAttribute("erro", erro);
            model.addAttribute("edicao", false);
            carregarOpcoes(model);
            return "agendamento-form";
        }

        try {
            agendamentoService.salvar(agendamento);
            redirect.addFlashAttribute("mensagem", "Agendamento cadastrado com sucesso.");
            return "redirect:/";
        } catch (DataAccessException ex) {
            model.addAttribute("erro", "Não foi possível salvar o agendamento. Confira os dados e tente novamente.");
            model.addAttribute("edicao", false);
            carregarOpcoes(model);
            return "agendamento-form";
        }
    }

    @PostMapping("/agendamentos/atualizar/{id}")
    public String atualizar(@PathVariable Integer id,
                            @ModelAttribute("agendamento") AgendamentoEntity agendamento,
                            Model model,
                            RedirectAttributes redirect) {
        agendamento.setAgendamentoId(id);
        String erro = validarCampos(agendamento);
        if (erro == null) {
            erro = validarReferencias(agendamento);
        }
        if (erro != null) {
            model.addAttribute("erro", erro);
            model.addAttribute("edicao", true);
            carregarOpcoes(model);
            return "agendamento-form";
        }

        try {
            agendamentoService.atualizar(id, agendamento);
            redirect.addFlashAttribute("mensagem", "Agendamento atualizado com sucesso.");
            return "redirect:/";
        } catch (ResponseStatusException ex) {
            redirect.addFlashAttribute("erro", "Agendamento não encontrado.");
            return "redirect:/";
        } catch (DataAccessException ex) {
            model.addAttribute("erro", "Não foi possível atualizar o agendamento. Confira os dados e tente novamente.");
            model.addAttribute("edicao", true);
            carregarOpcoes(model);
            return "agendamento-form";
        }
    }

    @PostMapping("/agendamentos/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            agendamentoService.excluir(id);
            redirect.addFlashAttribute("mensagem", "Agendamento excluído com sucesso.");
        } catch (ResponseStatusException ex) {
            redirect.addFlashAttribute("erro", "Agendamento não encontrado.");
        } catch (DataAccessException ex) {
            redirect.addFlashAttribute("erro", "Não foi possível excluir o agendamento informado.");
        }
        return "redirect:/";
    }

    private void carregarOpcoes(Model model) {
        model.addAttribute("clientes", clienteService.buscar());
        model.addAttribute("profissionais", profissionalService.buscar());
    }

    private String validarCampos(AgendamentoEntity agendamento) {
        if (agendamento.getClienteId() == null) {
            return "Selecione um cliente.";
        }
        if (agendamento.getProfissionalId() == null) {
            return "Selecione um profissional.";
        }
        if (agendamento.getAgendamentoData() == null) {
            return "Informe a data do agendamento.";
        }
        if (agendamento.getAgendamentoHorario() == null) {
            return "Informe o horário do agendamento.";
        }
        return null;
    }

    private String validarReferencias(AgendamentoEntity agendamento) {
        try {
            clienteService.buscarPorId(agendamento.getClienteId());
            profissionalService.buscarPorId(agendamento.getProfissionalId());
            return null;
        } catch (ResponseStatusException ex) {
            return "Selecione um cliente e um profissional cadastrados.";
        }
    }
}
