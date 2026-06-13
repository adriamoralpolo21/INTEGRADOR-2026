package com.glovo.app.controllers;

import com.glovo.app.entity.CategoriaProducto;
import com.glovo.app.services.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping("/comida")
    public String comida(Model model) {
        model.addAttribute("productos",
                productoService.listarPorCategoria(CategoriaProducto.COMIDA));
        return "comida";   // <- nombre del HTML: templates/comida.html
    }

    @GetMapping("/farmacia")
    public String farmacia(Model model) {
        model.addAttribute("productos",
                productoService.listarPorCategoria(CategoriaProducto.FARMACIA));
        return "farmacia"; // <- templates/farmacia.html
    }

    @GetMapping("/tiendas")
    public String tiendas(Model model) {
        model.addAttribute("productos",
                productoService.listarPorCategoria(CategoriaProducto.TIENDA));
        return "tiendas";  // <- templates/tiendas.html
    }

    @GetMapping("/supermercado")
    public String supermercado(Model model) {
        model.addAttribute("productos",
                productoService.listarPorCategoria(CategoriaProducto.SUPERMERCADO));
        return "supermercado"; // <- templates/supermercado.html
    }
}
