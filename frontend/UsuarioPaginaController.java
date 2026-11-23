package com.consumo.api.controller;

import com.consumo.api.entity.UsuarioEntity;
import com.consumo.api.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller apenas para telas Thymeleaf.
 * O UsuarioController REST original permanece INALTERADO (/consumoapi).
 * As telas usam o UsuarioService já existente; não há regras duplicadas.
 */
@Controller
@RequestMapping("/usuarios")
public class UsuarioPaginaController {

    private final UsuarioService usuarioService;

    // Injeção de dependência por construtor: funciona com e sem Lombok.
    public UsuarioPaginaController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Tela inicial: http://localhost:8080/usuarios
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.buscar());
        return "usuarios";
    }

    // Exibe formulário para um novo cadastro.
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new UsuarioEntity());
        model.addAttribute("edicao", false);
        return "usuario-form";
    }

    // Formulário HTML envia POST; a API REST continua usando seus métodos originais.
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("usuario") UsuarioEntity usuario,
                         Model model, RedirectAttributes redirect) {

        String erro = validarCamposBasicos(usuario);
        if (erro != null) {
            model.addAttribute("erro", erro);
            model.addAttribute("edicao", false);
            return "usuario-form";
        }

        try {
            usuarioService.salvar(usuario);
            redirect.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso.");
            return "redirect:/usuarios";
        } catch (RuntimeException ex) {
            model.addAttribute("erro", "Não foi possível cadastrar. Verifique os dados, especialmente e-mail e CPF já utilizados.");
            model.addAttribute("edicao", false);
            return "usuario-form";
        }
    }

    // Carrega o cadastro existente para preencher o formulário.
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model, RedirectAttributes redirect) {
        try {
            UsuarioEntity usuario = usuarioService.buscarPorId(id);
            if (usuario == null) {
                redirect.addFlashAttribute("erro", "Usuário não encontrado.");
                return "redirect:/usuarios";
            }
            model.addAttribute("usuario", usuario);
            model.addAttribute("edicao", true);
            return "usuario-form";
        } catch (RuntimeException ex) {
            redirect.addFlashAttribute("erro", "Usuário não encontrado.");
            return "redirect:/usuarios";
        }
    }

    // O formulário utiliza POST para atualizar, sem alterar o PUT da API REST.
    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Integer id,
                            @ModelAttribute("usuario") UsuarioEntity usuario,
                            Model model, RedirectAttributes redirect) {

        String erro = validarCamposBasicos(usuario);
        if (erro != null) {
            usuario.setId(id);
            model.addAttribute("erro", erro);
            model.addAttribute("edicao", true);
            return "usuario-form";
        }

        try {
            UsuarioEntity atualizado = usuarioService.atualizar(id, usuario);
            if (atualizado == null) {
                redirect.addFlashAttribute("erro", "Usuário não encontrado.");
            } else {
                redirect.addFlashAttribute("mensagem", "Usuário atualizado com sucesso.");
            }
            return "redirect:/usuarios";
        } catch (RuntimeException ex) {
            usuario.setId(id);
            model.addAttribute("erro", "Não foi possível atualizar. Verifique se o usuário existe e se os dados são válidos.");
            model.addAttribute("edicao", true);
            return "usuario-form";
        }
    }

    // Exclusão via formulário HTML POST; DELETE /consumoapi/{id} permanece igual.
    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            usuarioService.excluir(id);
            redirect.addFlashAttribute("mensagem", "Operação de exclusão concluída.");
        } catch (RuntimeException ex) {
            redirect.addFlashAttribute("erro", "Não foi possível excluir o usuário informado.");
        }
        return "redirect:/usuarios";
    }

    // Validação simples para formulários; não altera a Entity nem a API REST.
    private String validarCamposBasicos(UsuarioEntity usuario) {
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            return "Informe o nome do usuário.";
        }

        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            return "Informe o e-mail do usuário.";
        }

        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            return "Informe a senha (inclusive nas atualizações).";
        }

        return null;
    }
}
