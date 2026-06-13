package com.glovo.app.controllers;

import com.glovo.app.dto.SoporteForm;
import com.glovo.app.services.SoporteService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SoporteController {

    private final SoporteService soporteService;

    public SoporteController(SoporteService soporteService) {
        this.soporteService = soporteService;
    }

    @GetMapping("/soporte")
    public String soporte(Model model, Authentication auth) {
        SoporteForm form = (SoporteForm) model.asMap().get("soporteForm");
        if (form == null) form = new SoporteForm();

        // Si hay sesión, pre-llenamos el email (y dejamos nombre en blanco si no lo conoces)
        if (auth != null && auth.isAuthenticated()) {
            form.setEmail(auth.getName()); // normalmente el username = email
        }
        model.addAttribute("soporteForm", form);
        return "soporte";
    }

    @PostMapping("/soporte/enviar")
    public String enviar(@Valid @ModelAttribute("soporteForm") SoporteForm form,
                         BindingResult br,
                         RedirectAttributes ra,
                         Authentication auth) {
        if (br.hasErrors()) return "soporte";

        // Si está logueado, forzamos el email a ser el de la sesión (seguridad)
        if (auth != null && auth.isAuthenticated()) {
            form.setEmail(auth.getName());
        }

        soporteService.crearTicket(form);
        ra.addFlashAttribute("ok", "¡Tu mensaje fue enviado, gracias!");
        return "redirect:/soporte";
    }
}
