package com.glovo.app.controllers;

import com.glovo.app.entity.CategoriaProducto;
import com.glovo.app.entity.Producto;
import com.glovo.app.services.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/productos")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ProductoAdminController {

    private final ProductoService productoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "admin/productos-lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", CategoriaProducto.values());
        return "admin/productos-form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("producto") Producto producto,
                          BindingResult result,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", CategoriaProducto.values());
            return "admin/productos-form";
        }
        productoService.guardar(producto);
        return "redirect:/admin/productos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var opt = productoService.buscarPorId(id);
        if (opt.isEmpty()) {
            return "redirect:/admin/productos";
        }
        model.addAttribute("producto", opt.get());
        model.addAttribute("categorias", CategoriaProducto.values());
        return "admin/productos-form";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/admin/productos";
    }
}
