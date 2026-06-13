package com.glovo.app.services;

import com.glovo.app.entity.CategoriaProducto;
import com.glovo.app.entity.Producto;
import com.glovo.app.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public List<Producto> listarPorCategoria(CategoriaProducto categoria) {
        return productoRepository.findByDisponibleTrueAndCategoria(categoria);
    }

    public List<Producto> listarDestacados() {
        return productoRepository.findByDestacadoTrue();
    }

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}
