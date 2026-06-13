package com.glovo.app.controllers;

import com.glovo.app.entity.EstadoPedido;
import com.glovo.app.entity.Pedido;
import com.glovo.app.services.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/repartidor")
public class RepartidorController {

    private final PedidoService pedidoService;

    public RepartidorController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // 🟢 LISTA DE PEDIDOS AGRUPADOS POR ESTADO
    @GetMapping("/pedidos")
    public String verPedidos(Model model) {

        List<Pedido> pendientes  = pedidoService.listarPedidosPendientes();
        List<Pedido> enCamino    = pedidoService.listarPedidosEnCamino();
        List<Pedido> enEspera    = pedidoService.listarPedidosEnEspera();
        List<Pedido> entregados  = pedidoService.listarPedidosEntregados();   // ✅ NUEVO
        List<Pedido> cancelados  = pedidoService.listarPedidosCancelados();   // ✅ NUEVO

        model.addAttribute("pendientes", pendientes);
        model.addAttribute("enCamino", enCamino);
        model.addAttribute("enEspera", enEspera);
        model.addAttribute("entregados", entregados);  // ✅ para la vista
        model.addAttribute("cancelados", cancelados);  // ✅ para la vista

        return "repartidor/pedidos";
    }

    // 🟡 MARCAR COMO EN CAMINO (se queda en la lista, pero pasa a la sección "En camino")
    @PostMapping("/pedidos/{id}/en-camino")
    public String marcarEnCamino(@PathVariable Long id,
                                 RedirectAttributes attrs) {
        try {
            pedidoService.cambiarEstado(id, EstadoPedido.EN_CAMINO);
            attrs.addFlashAttribute("ok", "Pedido #" + id + " marcado como EN CAMINO.");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", "No se pudo actualizar el pedido #" + id + ".");
        }
        return "redirect:/repartidor/pedidos";
    }

    // 🟢 MARCAR COMO ENTREGADO
    // ahora usa marcarEntregado() para también actualizar EstadoPago cuando toque
    @PostMapping("/pedidos/{id}/entregado")
    public String marcarEntregado(@PathVariable Long id,
                                  RedirectAttributes attrs) {
        try {
            pedidoService.marcarEntregado(id); // ✅ usa la lógica que también toca estadoPago
            attrs.addFlashAttribute("ok", "Pedido #" + id + " marcado como ENTREGADO.");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", "No se pudo actualizar el pedido #" + id + ".");
        }
        return "redirect:/repartidor/pedidos";
    }

    // 🔍 DETALLE DEL PEDIDO PARA REPARTIDOR
    @GetMapping("/pedidos/{id}")
    public String detallePedido(@PathVariable Long id,
                                Model model,
                                RedirectAttributes attrs) {
        try {
            Pedido pedido = pedidoService.obtenerPedidoPorId(id);
            model.addAttribute("pedido", pedido);
            return "repartidor/pedido-detalle";
        } catch (Exception e) {
            attrs.addFlashAttribute("error", "No se encontró el pedido #" + id + ".");
            return "redirect:/repartidor/pedidos";
        }
    }
}
