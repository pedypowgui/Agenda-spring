package com.consumo.api.controller;

import com.consumo.api.entity.ClienteEntity;
import com.consumo.api.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/clientes"})
public class ClientePaginaController {

    private final ClienteService clienteService;

    public ClientePaginaController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.buscar());
        return "cliente";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new ClienteEntity());
        model.addAttribute("edicao", false);
        return "cliente-form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("cliente") ClienteEntity cliente, Model model, RedirectAttributes redirect) {
        String erro = validarCampos(cliente);
        if (erro != null) {
            model.addAttribute("erro", erro);
            model.addAttribute("edicao", false);
            return "cliente-form";
        }

        try {
            clienteService.salvar(cliente);
            redirect.addFlashAttribute("mensagem", "Cliente cadastrado com sucesso.");
            return "redirect:/clientes";
        } catch (RuntimeException ex) {
            model.addAttribute("erro", "Não foi possível cadastrar. Verifique se o e-mail ou CPF já estão em uso.");
            model.addAttribute("edicao", false);
            return "cliente-form";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model, RedirectAttributes redirect) {
        try {
            model.addAttribute("cliente", clienteService.buscarPorId(id));
            model.addAttribute("edicao", true);
            return "cliente-form";
        } catch (RuntimeException ex) {
            redirect.addFlashAttribute("erro", "Cliente não encontrado.");
            return "redirect:/clientes";
        }
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Integer id, @ModelAttribute("cliente") ClienteEntity cliente, Model model, RedirectAttributes redirect) {
        cliente.setClienteId(id);
        String erro = validarCampos(cliente);
        if (erro != null) {
            model.addAttribute("erro", erro);
            model.addAttribute("edicao", true);
            return "cliente-form";
        }

        try {
            clienteService.atualizar(id, cliente);
            redirect.addFlashAttribute("mensagem", "Cliente atualizado com sucesso.");
            return "redirect:/clientes";
        } catch (RuntimeException ex) {
            model.addAttribute("erro", "Não foi possível atualizar. Verifique se o e-mail ou CPF já estão em uso.");
            model.addAttribute("edicao", true);
            return "cliente-form";
        }
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            clienteService.excluir(id);
            redirect.addFlashAttribute("mensagem", "Cliente excluído com sucesso.");
        } catch (RuntimeException ex) {
            redirect.addFlashAttribute("erro", "Não foi possível excluir o cliente informado.");
        }
        return "redirect:/clientes";
    }

    private String validarCampos(ClienteEntity cliente) {
        if (cliente.getClienteNome() == null || cliente.getClienteNome().isBlank()) {
            return "Informe o nome do cliente.";
        }
        if (cliente.getClienteEmail() == null || cliente.getClienteEmail().isBlank()) {
            return "Informe o e-mail do cliente.";
        }
        if (cliente.getClienteTelefone() == null || cliente.getClienteTelefone().isBlank()) {
            return "Informe o telefone do cliente.";
        }
        if (cliente.getClienteCpf() == null || cliente.getClienteCpf().isBlank()) {
            return "Informe o CPF do cliente.";
        }
        return null;
    }
}
