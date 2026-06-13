package com.glovo.app.controllers;

import com.glovo.app.dto.cart.CartResponseDto;
import com.glovo.app.entity.Pedido;
import com.glovo.app.entity.Usuario;
import com.glovo.app.services.CartService;
import com.glovo.app.services.PedidoService;
import com.glovo.app.services.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
public class CheckoutController {

    private final CartService cartService;
    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;

    public CheckoutController(CartService cartService,
            PedidoService pedidoService,
            UsuarioService usuarioService) {
        this.cartService = cartService;
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/checkout")
    public String checkout(Authentication auth, Model model) {

        CartResponseDto snap = cartService.getCart(auth);

        if (snap.getItems().isEmpty()) {
            return "redirect:/?emptyCart";
        }

        model.addAttribute("items", snap.getItems());
        model.addAttribute("subTotal", snap.getTotal());
        model.addAttribute("envio", BigDecimal.ZERO);
        model.addAttribute("total", snap.getTotal());
        model.addAttribute("pedidoConfirmado", false);

        // 👇 NUEVO: pre-cargar dirección guardada
        if (auth != null) {
            Optional<Usuario> opt = usuarioService.obtenerPorEmail(auth.getName());
            opt.ifPresent(u -> {
                model.addAttribute("direccionInicial", u.getDireccionEntrega());
                model.addAttribute("latitudInicial", u.getLatitudEntrega());
                model.addAttribute("longitudInicial", u.getLongitudEntrega());
            });
        }

        return "checkout";
    }

 @PostMapping("/orden/confirmar")
public String confirmarOrden(
        Authentication auth,
        @RequestParam("direccion") String direccion,
        @RequestParam(value = "referencia", required = false) String referencia,
        @RequestParam("pago") String metodoPago,
        @RequestParam(value = "latitud", required = false) Double latitud,
        @RequestParam(value = "longitud", required = false) Double longitud,
        // 👇 NUEVOS CAMPOS SOLO PARA CONTRAENTREGA
        @RequestParam(value = "contraNombre", required = false) String contraNombre,
        @RequestParam(value = "contraTelefono", required = false) String contraTelefono,
        @RequestParam(value = "contraMonto", required = false) String contraMonto,
        RedirectAttributes redirectAttrs
) {

    try {
        // Dirección mínimamente válida
        if (direccion == null || direccion.trim().length() < 5) {
            redirectAttrs.addFlashAttribute("error", "Ingresa una dirección válida.");
            return "redirect:/checkout";
        }

        // 🔹 Validar datos de contraentrega si corresponde
        if ("contraentrega".equalsIgnoreCase(metodoPago)) {

            boolean nombreOk   = contraNombre != null   && contraNombre.trim().length() >= 3;
            boolean telOk      = contraTelefono != null && contraTelefono.trim().length() >= 6;
            boolean montoOk    = contraMonto != null    && !contraMonto.trim().isBlank();

            if (!nombreOk || !telOk || !montoOk) {
                redirectAttrs.addFlashAttribute(
                        "error",
                        "Completa todos los datos de pago contraentrega (nombre, teléfono y monto)."
                );
                return "redirect:/checkout";
            }
        }

        // Actualizar dirección preferida del usuario
        if (auth != null) {
            usuarioService.actualizarUbicacionEntrega(auth.getName(), direccion, latitud, longitud);
        }

        // Crear pedido normalmente
        Pedido pedido = pedidoService.crearPedidoDesdeCarrito(auth, direccion, referencia, metodoPago);

        redirectAttrs.addFlashAttribute("ok",
                "Tu pedido #" + pedido.getId() + " fue registrado correctamente 🛍️");

        return "redirect:/mis-pedidos";

    } catch (IllegalStateException ex) {
        redirectAttrs.addFlashAttribute("error", ex.getMessage());
        return "redirect:/checkout";

    } catch (Exception ex) {
        ex.printStackTrace();
        redirectAttrs.addFlashAttribute("error",
                "Ocurrió un error al procesar tu pedido. Inténtalo nuevamente.");
        return "redirect:/checkout";
    }
}


}
