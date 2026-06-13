package com.glovo.app.repository;

import com.glovo.app.entity.CarritoItem;
import com.glovo.app.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {

    List<CarritoItem> findByUsuario(Usuario usuario);

    void deleteByUsuario(Usuario usuario);

    Optional<CarritoItem> findByUsuarioAndCodigoProducto(Usuario usuario, String codigoProducto);
}
