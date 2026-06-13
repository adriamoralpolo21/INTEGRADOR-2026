package com.glovo.app.repository;

import com.glovo.app.entity.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {
     @Query("SELECT d.nombreProducto, SUM(d.cantidad) " +
           "FROM PedidoDetalle d " +
           "GROUP BY d.nombreProducto " +
           "ORDER BY SUM(d.cantidad) DESC")
    List<Object[]> topProductos();

}
