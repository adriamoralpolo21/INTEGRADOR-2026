package com.glovo.app.controllers;

import com.glovo.app.services.AdminEstadisticasService;
import com.glovo.app.services.SoporteService;
import com.glovo.app.services.UsuarioAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final UsuarioAdminService usuarioAdminService;
    private final SoporteService soporteService;
    private final AdminEstadisticasService adminEstadisticasService; // ✅ nuevo

    @GetMapping
    public String dashboard(Model model) {

        // Usuarios
        
        model.addAttribute("totalUsuarios", usuarioAdminService.contarUsuarios());
        model.addAttribute("totalAdmins", usuarioAdminService.contarAdmins());
        model.addAttribute("totalUsers", usuarioAdminService.contarUsers());
        model.addAttribute("usuariosActivos", usuarioAdminService.contarActivos());
        model.addAttribute("usuariosBloqueados", usuarioAdminService.contarBloqueados());

        // Soporte
        model.addAttribute("ticketsActivos", soporteService.contarTicketsActivos());
        model.addAttribute("ticketsArchivados", soporteService.contarTicketsArchivados());

        // ✅ Métricas de pedidos / ventas
        model.addAttribute("ventasHoy", adminEstadisticasService.getVentasHoy());
        model.addAttribute("pedidosHoy", adminEstadisticasService.getPedidosHoy());
        model.addAttribute("ventasMes", adminEstadisticasService.getVentasMesActual());
        model.addAttribute("metodoMasUsado", adminEstadisticasService.getMetodoPagoMasUsado());


        // ✅ Datos para gráfico por estado
        model.addAttribute("labelsEstados", adminEstadisticasService.getLabelsEstados());
        model.addAttribute("valoresEstados", adminEstadisticasService.getValoresEstados());
        model.addAttribute("topProductos", adminEstadisticasService.getTopProductos());

        return "admin/dashboard";
    }
}
