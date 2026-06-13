package com.glovo.app.services;

import com.glovo.app.dto.cart.CartItemDto;
import com.glovo.app.dto.cart.CartResponseDto;
import com.glovo.app.entity.CarritoItem;
import com.glovo.app.entity.Usuario;
import com.glovo.app.repository.CarritoItemRepository;
import com.glovo.app.repository.UsuarioRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CarritoItemRepository carritoRepo;
    private final UsuarioRepository usuarioRepo;

    public CartService(CarritoItemRepository carritoRepo,
                       UsuarioRepository usuarioRepo) {
        this.carritoRepo = carritoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    // ================= API pública =================

    public CartResponseDto getCart(Authentication auth) {
        Usuario usuario = getUsuario(auth);
        if (usuario == null) {
            return emptyCart();
        }
        List<CarritoItem> items = carritoRepo.findByUsuario(usuario);
        return mapToResponse(items);
    }

    public CartResponseDto addItem(Authentication auth,
                                   String codigo,
                                   String nombre,
                                   String precioStr,
                                   String imagenUrl,
                                   String servicio,
                                   int cantidad) {

        Usuario usuario = getUsuario(auth);
        if (usuario == null) {
            // si por alguna razón no hay usuario, devolvemos carrito vacío sin romper
            return emptyCart();
        }

        if (cantidad < 1) cantidad = 1;

        BigDecimal precio = parsePrecio(precioStr);

        Optional<CarritoItem> opt = carritoRepo.findByUsuarioAndCodigoProducto(usuario, codigo);
        CarritoItem item;
        if (opt.isPresent()) {
            item = opt.get();
            item.setCantidad(item.getCantidad() + cantidad);
            // si por alguna razón cambió el precio, lo actualizamos
            item.setPrecioUnitario(precio);
        } else {
            item = new CarritoItem();
            item.setUsuario(usuario);
            item.setCodigoProducto(codigo);
            item.setNombreProducto(nombre);
            item.setPrecioUnitario(precio);
            item.setCantidad(cantidad);
            item.setImagenUrl(imagenUrl);
            item.setServicio(servicio);
        }
        carritoRepo.save(item);

        return getCart(auth);
    }

    public CartResponseDto removeOne(Authentication auth, String codigo) {
        Usuario usuario = getUsuario(auth);
        if (usuario == null) {
            return emptyCart();
        }
        carritoRepo.findByUsuarioAndCodigoProducto(usuario, codigo)
                .ifPresent(item -> {
                    int nueva = item.getCantidad() - 1;
                    if (nueva <= 0) {
                        carritoRepo.delete(item);
                    } else {
                        item.setCantidad(nueva);
                        carritoRepo.save(item);
                    }
                });
        return getCart(auth);
    }

    public CartResponseDto setCantidad(Authentication auth, String codigo, int cantidad) {
        Usuario usuario = getUsuario(auth);
        if (usuario == null) {
            return emptyCart();
        }
        carritoRepo.findByUsuarioAndCodigoProducto(usuario, codigo)
                .ifPresent(item -> {
                    if (cantidad <= 0) {
                        carritoRepo.delete(item);
                    } else {
                        item.setCantidad(cantidad);
                        carritoRepo.save(item);
                    }
                });
        return getCart(auth);
    }

    // 🔴 AQUÍ ESTABA EL PROBLEMA
    public CartResponseDto clear(Authentication auth) {
        Usuario usuario = getUsuario(auth);
        if (usuario == null) {
            return emptyCart();
        }

        // ✅ En vez de usar deleteByUsuario (que no está funcionando bien),
        // usamos el findByUsuario que ya sabemos que funciona y borramos todo.
        List<CarritoItem> items = carritoRepo.findByUsuario(usuario);
        if (!items.isEmpty()) {
            carritoRepo.deleteAll(items);
        }

        // Devolvemos carrito vacío
        return emptyCart();
    }

    // ================= Helpers internos =================

    private Usuario getUsuario(Authentication auth) {
        if (auth == null ||
                auth instanceof AnonymousAuthenticationToken ||
                !auth.isAuthenticated()) {
            return null;
        }
        String email = auth.getName();
        if (email == null || "anonymousUser".equalsIgnoreCase(email)) {
            return null;
        }
        return usuarioRepo.findByEmail(email).orElse(null);
    }

    private BigDecimal parsePrecio(String precioStr) {
        if (precioStr == null || precioStr.isBlank()) {
            return BigDecimal.ZERO;
        }
        // limpiamos posibles símbolos "S/" y comas
        String limpio = precioStr.replace("S/", "")
                                 .replace("s/", "")
                                 .replace(" ", "")
                                 .replace(",", ".");
        try {
            return new BigDecimal(limpio);
        } catch (NumberFormatException e) {
            // si viene algo muy raro, no reventamos
            return BigDecimal.ZERO;
        }
    }

    private CartResponseDto emptyCart() {
        CartResponseDto resp = new CartResponseDto();
        resp.setItems(List.of());
        resp.setCount(0);
        resp.setTotal(BigDecimal.ZERO);
        return resp;
    }

    private CartResponseDto mapToResponse(List<CarritoItem> entityItems) {
        List<CartItemDto> items = entityItems.stream().map(ci -> {
            CartItemDto dto = new CartItemDto();
            dto.setId(ci.getCodigoProducto());
            dto.setNombre(ci.getNombreProducto());
            dto.setPrecio(ci.getPrecioUnitario());
            dto.setCantidad(ci.getCantidad());
            dto.setImagenUrl(ci.getImagenUrl());
            dto.setServicio(ci.getServicio());
            return dto;
        }).collect(Collectors.toList());

        CartResponseDto resp = new CartResponseDto();
        resp.setItems(items);

        int count = items.stream().mapToInt(CartItemDto::getCantidad).sum();
        resp.setCount(count);

        BigDecimal total = items.stream()
                .map(CartItemDto::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        resp.setTotal(total);

        return resp;
    }
}
