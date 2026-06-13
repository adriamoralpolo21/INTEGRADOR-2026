package com.glovo.app.controllers;

import com.glovo.app.services.AdminEstadisticasService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/estadisticas")
@RequiredArgsConstructor
public class AdminEstadisticasRestController {

    private final AdminEstadisticasService estadisticasService;

    /**
     * Endpoint para obtener ventas y pedidos según rango:
     *  rango = "hoy"  -> solo hoy
     *  rango = "7d"   -> últimos 7 días (hoy incluido)
     *  rango = "mes"  -> desde el 1 del mes actual hasta hoy
     */
    @GetMapping("/ventas")
    public Map<String, Object> ventasPorRango(
            @RequestParam(name = "rango", defaultValue = "hoy") String rango
    ) {
        LocalDate hoy = LocalDate.now();
        LocalDate desde;

        switch (rango) {
            case "7d" -> desde = hoy.minusDays(6);       // hoy y 6 días atrás
            case "mes" -> desde = hoy.withDayOfMonth(1); // desde el día 1 del mes
            default -> desde = hoy;                      // "hoy"
        }

        BigDecimal ventas = estadisticasService.getVentasEntre(desde, hoy);
        long pedidos = estadisticasService.getPedidosEntre(desde, hoy);

        return Map.of(
                "ventas", ventas,
                "pedidos", pedidos,
                "desde", desde.toString(),
                "hasta", hoy.toString(),
                "rango", rango
        );
    }
}
