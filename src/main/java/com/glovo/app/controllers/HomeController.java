package com.glovo.app.controllers;

import com.glovo.app.services.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UsuarioService usuarioService;

    public HomeController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping({"/", "/index"})
    public String index(Authentication auth, Model model) {

        if (auth != null) {
            usuarioService.obtenerPorEmail(auth.getName()).ifPresent(u -> {
                model.addAttribute("direccionInicial", u.getDireccionEntrega());
            });
        }

        return "index";
    }

    @GetMapping("/ofrecemos")
    public String ofrecemos() {
        return "ofrecemos";
    }
}
