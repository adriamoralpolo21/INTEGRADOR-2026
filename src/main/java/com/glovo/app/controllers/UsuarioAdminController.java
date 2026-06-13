package com.glovo.app.controllers;

import com.glovo.app.entity.Rol;
import com.glovo.app.services.UsuarioAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/usuarios")
@RequiredArgsConstructor
public class UsuarioAdminController {

    private final UsuarioAdminService usuarioAdminService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioAdminService.listarTodos());
        model.addAttribute("roles", Rol.values());
        return "admin/usuarios";
    }

    @PostMapping("/{id}/rol")
    public String cambiarRol(@PathVariable Long id, @RequestParam("rol") Rol rol) {
        usuarioAdminService.cambiarRol(id, rol);
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Long id, @RequestParam("enabled") boolean enabled) {
        usuarioAdminService.cambiarEstado(id, enabled);
        return "redirect:/admin/usuarios";
    }
}
