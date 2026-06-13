package com.glovo.app.services;

import com.glovo.app.entity.EstadoPedido;
import com.glovo.app.entity.MetodoPago;
import com.glovo.app.repository.PedidoDetalleRepository;
import com.glovo.app.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class AdminEstadisticasService {

    private final PedidoRepository pedidoRepository;
      private final PedidoDetalleRepository pedidoDetalleRepository;

    public BigDecimal getVentasHoy() {
        BigDecimal v = pedidoRepository.ventasHoy();
        return v != null ? v : BigDecimal.ZERO;
    }

    public long getPedidosHoy() {
        return pedidoRepository.pedidosHoy();
    }

    public BigDecimal getVentasMesActual() {
        BigDecimal v = pedidoRepository.ventasMesActual();
        return v != null ? v : BigDecimal.ZERO;
    }

    // ✅ AHORA usa el String que devuelve el repo
    public String getMetodoPagoMasUsado() {
        String codigo = pedidoRepository.metodoPagoMasUsadoRaw();

        if (codigo == null || codigo.isBlank()) {
            return "Sin datos";
        }

        try {
            MetodoPago mp = MetodoPago.valueOf(codigo);

            // Texto amigable para el dashboard
            return switch (mp) {
                case CONTRAENTREGA -> "Contraentrega (efectivo)";
                case YAPE         -> "Yape / Plin";
                case TARJETA      -> "Tarjeta";
                default           -> mp.name();
            };

        } catch (IllegalArgumentException ex) {
            // Si por algún motivo no matchea con el enum, devolvemos el código tal cual
            return codigo;
        }
    }

    public List<String> getLabelsEstados() {
        List<Object[]> crudos = pedidoRepository.conteoPorEstado();
        List<String> labels = new ArrayList<>();
        for (Object[] row : crudos) {
            EstadoPedido estado = (EstadoPedido) row[0];
            labels.add(estado.name());
        }
        return labels;
    }

    public List<Long> getValoresEstados() {
        List<Object[]> crudos = pedidoRepository.conteoPorEstado();
        List<Long> valores = new ArrayList<>();
        for (Object[] row : crudos) {
            Long cantidad = (Long) row[1];
            valores.add(cantidad);
        }
        return valores;
    }

        // ✅ NUEVO: top 5 productos
    public List<TopProductoDto> getTopProductos() {
        List<Object[]> crudos = pedidoDetalleRepository.topProductos();
        List<TopProductoDto> lista = new ArrayList<>();

        for (Object[] row : crudos) {
            String nombre = (String) row[0];
            Long cantidad = ((Number) row[1]).longValue();
            lista.add(new TopProductoDto(nombre, cantidad));
        }
        return lista.stream().limit(5).toList();
    }

    // DTO interno súper simple
    public record TopProductoDto(String nombre, Long cantidad) {}
     // ==== Filtros por rango de fechas (hoy / 7 días / mes) ====

    public BigDecimal getVentasEntre(LocalDate desde, LocalDate hasta) {
        BigDecimal v = pedidoRepository.ventasEntreFechas(desde, hasta);
        return v != null ? v : BigDecimal.ZERO;
    }

    public long getPedidosEntre(LocalDate desde, LocalDate hasta) {
        return pedidoRepository.pedidosEntreFechas(desde, hasta);
    }

    

}
