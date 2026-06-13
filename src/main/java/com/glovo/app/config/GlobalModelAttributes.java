package com.glovo.app.config;

import com.glovo.app.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributes {

    private final PedidoService pedidoService;

    @ModelAttribute
    public void addGlobalAttributes(Model model, Authentication auth) {
        int pedidosActivos = 0;
        try {
            pedidosActivos = pedidoService.contarPedidosActivos(auth);
        } catch (Exception ignored) {
        }
        model.addAttribute("pedidosActivos", pedidosActivos);
    }
}
