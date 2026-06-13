package com.glovo.app.controllers;

import com.glovo.app.services.SoporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/soporte")
@RequiredArgsConstructor
public class SoporteAdminController {

    private final SoporteService soporteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("activos", soporteService.listarActivos());
        model.addAttribute("archivados", soporteService.listarArchivados());
        return "admin/soporte-lista";
    }

    @PostMapping("/{id}/archivar")
    public String archivar(@PathVariable Long id) {
        soporteService.archivarPorId(id);
        return "redirect:/admin/soporte";
    }

    @PostMapping("/{id}/restaurar")
    public String restaurar(@PathVariable Long id) {
        soporteService.restaurarPorId(id);
        return "redirect:/admin/soporte";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        soporteService.eliminarPorId(id);
        return "redirect:/admin/soporte";
    }
}
