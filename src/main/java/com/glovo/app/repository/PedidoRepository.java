package com.glovo.app.repository;

import com.glovo.app.entity.EstadoPedido;
import com.glovo.app.entity.Pedido;
import com.glovo.app.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByUsuarioOrderByFechaCreacionDesc(Usuario usuario);

    List<Pedido> findByEstadoOrderByFechaCreacionAsc(EstadoPedido estado);

    List<Pedido> findAllByOrderByFechaCreacionDesc();

    int countByUsuarioAndEstadoIn(Usuario usuario, List<EstadoPedido> estados);

    // ====== NUEVO: métricas para dashboard admin ======

    // Ventas de hoy
    @Query(value = "SELECT COALESCE(SUM(p.total),0) " +
            "FROM pedidos p " +
            "WHERE DATE(p.fecha_creacion) = CURRENT_DATE", nativeQuery = true)
    BigDecimal ventasHoy();

    // Pedidos de hoy
    @Query(value = "SELECT COUNT(*) " +
            "FROM pedidos p " +
            "WHERE DATE(p.fecha_creacion) = CURRENT_DATE", nativeQuery = true)
    long pedidosHoy();

    // Ventas del mes actual
    @Query(value = "SELECT COALESCE(SUM(p.total),0) " +
            "FROM pedidos p " +
            "WHERE YEAR(p.fecha_creacion) = YEAR(CURRENT_DATE) " +
            "AND MONTH(p.fecha_creacion) = MONTH(CURRENT_DATE)", nativeQuery = true)
    BigDecimal ventasMesActual();

    // Método de pago más usado (devuelve la fila ganadora)
    @Query(value = "SELECT p.metodo_pago " +
        "FROM pedidos p " +
        "GROUP BY p.metodo_pago " +
        "ORDER BY COUNT(*) DESC " +
        "LIMIT 1", nativeQuery = true)
String metodoPagoMasUsadoRaw();

    // Conteo de pedidos por estado para el gráfico
    @Query("SELECT p.estado, COUNT(p) FROM Pedido p GROUP BY p.estado")
    List<Object[]> conteoPorEstado();
  // ✅ ventas entre fechas
    @Query("SELECT COALESCE(SUM(p.total),0) " +
           "FROM Pedido p " +
           "WHERE p.fechaCreacion BETWEEN :desde AND :hasta")
    BigDecimal ventasEntreFechas(LocalDate desde, LocalDate hasta);

    @Query("SELECT COUNT(p) " +
           "FROM Pedido p " +
           "WHERE p.fechaCreacion BETWEEN :desde AND :hasta")
    long pedidosEntreFechas(LocalDate desde, LocalDate hasta);
    
}
