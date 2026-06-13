package com.glovo.app.controllers;

import com.glovo.app.entity.Pedido;
import com.glovo.app.services.PedidoService;
import com.glovo.app.services.PedidoPdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoPdfService pedidoPdfService; // 🔹 NUEVO

    public PedidoController(PedidoService pedidoService,
                            PedidoPdfService pedidoPdfService) { // 🔹 NUEVO
        this.pedidoService = pedidoService;
        this.pedidoPdfService = pedidoPdfService;   // 🔹 NUEVO
    }

    // 🟢 LISTAR MIS PEDIDOS
    @GetMapping("/mis-pedidos")
    public String misPedidos(Authentication auth, Model model) {

        List<Pedido> pedidos = pedidoService.listarPedidosUsuario(auth);
        model.addAttribute("pedidos", pedidos);

        return "mis-pedidos";
    }

    // 🟢 VER DETALLE DE UN PEDIDO
    @GetMapping("/pedido/{id}")
    public String detallePedido(@PathVariable Long id,
                                Authentication auth,
                                Model model) {
        Pedido pedido = pedidoService.obtenerPedidoUsuario(id, auth);
        model.addAttribute("pedido", pedido);
        return "pedido-detalle";
    }

    // 🟠 CANCELAR PEDIDO
    @PostMapping("/pedido/{id}/cancelar")
    public String cancelarPedido(@PathVariable Long id,
                                 Authentication auth) {
        try {
            pedidoService.cancelarPedido(id, auth);
            return "redirect:/mis-pedidos?cancelOk";
        } catch (Exception e) {
            return "redirect:/mis-pedidos?cancelError";
        }
    }

    // 🟢 REPETIR PEDIDO (rellena carrito y te manda al checkout)
    @PostMapping("/pedido/{id}/repetir")
    public String repetirPedido(@PathVariable Long id,
                                Authentication auth) {
        pedidoService.repetirPedido(id, auth);
        return "redirect:/checkout";
    }

    // 🟢 DESCARGAR PDF DEL PEDIDO
    @GetMapping("/mis-pedidos/{id}/pdf")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable Long id,
                                               Authentication auth) {

        Pedido pedido = pedidoService.obtenerPedidoUsuario(id, auth);
        byte[] pdf = pedidoPdfService.generarPdf(pedido);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "attachment", "pedido-" + id + ".pdf"
        );

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
    @PostMapping("/pedido/{id}/eliminar")
public String eliminarPedido(@PathVariable Long id,
                             Authentication auth) {
    try {
        pedidoService.eliminarPedidoUsuario(id, auth);
        return "redirect:/mis-pedidos?deleteOk";
    } catch (Exception e) {
        return "redirect:/mis-pedidos?deleteError";
    }
}


}
