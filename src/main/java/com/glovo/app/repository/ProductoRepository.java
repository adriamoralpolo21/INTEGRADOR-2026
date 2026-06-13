package com.glovo.app.repository;

import com.glovo.app.entity.CategoriaProducto;
import com.glovo.app.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoria(CategoriaProducto categoria);

    List<Producto> findByDisponibleTrue();

    List<Producto> findByDisponibleTrueAndCategoria(CategoriaProducto categoria);

    List<Producto> findByDestacadoTrue();
}
