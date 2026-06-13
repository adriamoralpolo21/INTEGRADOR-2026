package com.glovo.app.controllers;

import com.glovo.app.dto.RegistroForm;
import com.glovo.app.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "login"; // tu template login.html
    }

   @GetMapping("/register")
public String showRegister(Model model) {
    model.addAttribute("usuario", new RegistroForm());
    return "register";
}

@PostMapping("/register")
public String processRegister(@Valid @ModelAttribute("usuario") RegistroForm form,
                              BindingResult br,
                              Model model,
                              RedirectAttributes ra) {
    if (br.hasErrors()) return "register";

    try {
        usuarioService.registrar(form);
    } catch (IllegalArgumentException ex) {
        String msg = ex.getMessage().toLowerCase();
        if (msg.contains("correo")) {
            br.rejectValue("email", "", ex.getMessage());
        } else if (msg.contains("contrase")) {
            br.rejectValue("confirmPassword", "", ex.getMessage());
        } else if (msg.contains("número") || msg.contains("numero") || msg.contains("tel")) {
            br.rejectValue("telefono", "", ex.getMessage());
        } else {
            br.reject("", ex.getMessage());
        }
        return "register";
    }

    ra.addFlashAttribute("ok", "Cuenta creada con éxito. ¡Ahora inicia sesión!");
    return "redirect:/login?registered";
}
}