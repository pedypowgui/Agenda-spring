package com.consumo.api.controller;

import com.consumo.api.entity.ProfissionalEntity;
import com.consumo.api.service.ProfissionalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profissionais")
public class ProfissionalPaginaController {

    private final ProfissionalService profissionalService;

    public ProfissionalPaginaController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("profissionais", profissionalService.buscar());
        return "profissional";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("profissional", new ProfissionalEntity());
        model.addAttribute("edicao", false);
        return "profissional-form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("profissional") ProfissionalEntity profissional,
                         Model model,
                         RedirectAttributes redirect) {
        String erro = validarCampos(profissional);
        if (erro != null) {
            model.addAttribute("erro", erro);
            model.addAttribute("edicao", false);
            return "profissional-form";
        }

        try {
            profissionalService.salvar(profissional);
            redirect.addFlashAttribute("mensagem", "Profissional cadastrado com sucesso.");
            return "redirect:/profissionais";
        } catch (RuntimeException ex) {
            model.addAttribute("erro", "Não foi possível cadastrar. Verifique se o e-mail já está em uso.");
            model.addAttribute("edicao", false);
            return "profissional-form";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id,
                         Model model,
                         RedirectAttributes redirect) {
        try {
            model.addAttribute("profissional", profissionalService.buscarPorId(id));
            model.addAttribute("edicao", true);
            return "profissional-form";
        } catch (RuntimeException ex) {
            redirect.addFlashAttribute("erro", "Profissional não encontrado.");
            return "redirect:/profissionais";
        }
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Integer id,
                            @ModelAttribute("profissional") ProfissionalEntity profissional,
                            Model model,
                            RedirectAttributes redirect) {
        profissional.setProfissionalId(id);
        String erro = validarCampos(profissional);
        if (erro != null) {
            model.addAttribute("erro", erro);
            model.addAttribute("edicao", true);
            return "profissional-form";
        }

        try {
            profissionalService.atualizar(id, profissional);
            redirect.addFlashAttribute("mensagem", "Profissional atualizado com sucesso.");
            return "redirect:/profissionais";
        } catch (RuntimeException ex) {
            model.addAttribute("erro", "Não foi possível atualizar. Verifique se o e-mail está em uso.");
            model.addAttribute("edicao", true);
            return "profissional-form";
        }
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            profissionalService.excluir(id);
            redirect.addFlashAttribute("mensagem", "Profissional excluído com sucesso.");
        } catch (RuntimeException ex) {
            redirect.addFlashAttribute("erro", "Não foi possível excluir o profissional informado.");
        }
        return "redirect:/profissionais";
    }

    private String validarCampos(ProfissionalEntity profissional) {
        if (profissional.getProfissionalNome() == null || profissional.getProfissionalNome().isBlank()) {
            return "Informe o nome do profissional.";
        }
        if (profissional.getProfissionalEspecialidade() == null
                || profissional.getProfissionalEspecialidade().isBlank()) {
            return "Informe a especialidade do profissional.";
        }
        if (profissional.getProfissionalTelefone() == null || profissional.getProfissionalTelefone().isBlank()) {
            return "Informe o telefone do profissional.";
        }
        if (profissional.getProfissionalEmail() == null || profissional.getProfissionalEmail().isBlank()) {
            return "Informe o e-mail do profissional.";
        }
        return null;
    }
}
