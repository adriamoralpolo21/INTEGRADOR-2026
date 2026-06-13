package com.glovo.app.controllers;

import com.glovo.app.entity.Pedido;
import com.glovo.app.entity.EstadoPedido;
import com.glovo.app.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/pedidos")
@RequiredArgsConstructor
public class AdminPedidosController {

    private final PedidoService pedidoService;

    @GetMapping
    public String listar(Model model) {
        List<Pedido> pedidos = pedidoService.listarTodosAdmin();
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("estados", EstadoPedido.values());
        return "admin/pedidos-lista";
    }

    // cambiar estado desde admin
    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Long id,
                                @RequestParam("estado") EstadoPedido nuevoEstado) {
        pedidoService.cambiarEstado(id, nuevoEstado);
        return "redirect:/admin/pedidos";
    }
}
