package com.glovo.app.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.glovo.app.dto.cart.CartItemDto;
import com.glovo.app.dto.cart.CartResponseDto;
import com.glovo.app.entity.EstadoPago;
import com.glovo.app.entity.EstadoPedido;
import com.glovo.app.entity.MetodoPago;
import com.glovo.app.entity.Pedido;
import com.glovo.app.entity.PedidoDetalle;
import com.glovo.app.entity.Usuario;
import com.glovo.app.repository.PedidoRepository;
import com.glovo.app.repository.UsuarioRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CartService cartService;

    public PedidoService(PedidoRepository pedidoRepository,
                         UsuarioRepository usuarioRepository,
                         CartService cartService) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cartService = cartService;
    }

    // ====================================================
    // CREAR PEDIDO DESDE EL CARRITO
    // ====================================================
    public Pedido crearPedidoDesdeCarrito(Authentication auth,
                                          String direccion,
                                          String referencia,
                                          String metodoPago) {

        Usuario usuario = getUsuario(auth);
        if (usuario == null) {
            throw new IllegalStateException("Debe iniciar sesión para crear un pedido.");
        }

        CartResponseDto carrito = cartService.getCart(auth);
        if (carrito.getItems().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío.");
        }

        BigDecimal subtotal = carrito.getTotal();
        BigDecimal envio = BigDecimal.ZERO; // aquí podrías aplicar lógica de delivery
        BigDecimal total = subtotal.add(envio);

        // Convertimos el String del formulario a enum MetodoPago
        MetodoPago mp = MetodoPago.fromCodigo(metodoPago);

Pedido pedido = new Pedido();
pedido.setUsuario(usuario);
pedido.setDireccionEntrega(direccion);
pedido.setReferencia(referencia);
pedido.setMetodoPago(mp);
pedido.setEstado(EstadoPedido.PENDIENTE);

// 👇 Lógica de estado de pago según el método
EstadoPago estadoPagoInicial = EstadoPago.PENDIENTE;

if (mp == MetodoPago.YAPE || mp == MetodoPago.TARJETA) {
    // Yape / Plin y Tarjeta se consideran pagados al crear el pedido
    estadoPagoInicial = EstadoPago.PAGADO;
}

pedido.setEstadoPago(estadoPagoInicial);

pedido.setSubtotal(subtotal);
pedido.setCostoEnvio(envio);
pedido.setTotal(total);

        // Detalles
        for (CartItemDto item : carrito.getItems()) {
            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setPedido(pedido);
            detalle.setCodigoProducto(item.getId());
            detalle.setNombreProducto(item.getNombre());
            detalle.setPrecioUnitario(item.getPrecio());
            detalle.setCantidad(item.getCantidad());
            detalle.setSubtotal(item.getSubtotal());
            detalle.setImagenUrl(item.getImagenUrl());
            detalle.setServicio(item.getServicio());

            pedido.addDetalle(detalle);
        }

        // Guardamos el pedido
        Pedido guardado = pedidoRepository.save(pedido);

        // Intentamos limpiar el carrito, pero si falla NO rompemos el flujo
        try {
            cartService.clear(auth);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return guardado;
    }

    // ====================================================
    // LISTAR PEDIDOS DEL USUARIO
    // ====================================================
    public List<Pedido> listarPedidosUsuario(Authentication auth) {
        Usuario usuario = getUsuario(auth);
        if (usuario == null) {
            throw new IllegalStateException("Debe iniciar sesión.");
        }
        return pedidoRepository.findByUsuarioOrderByFechaCreacionDesc(usuario);
    }

    // ====================================================
    // OBTENER UN PEDIDO DEL USUARIO (para detalle / repetir / cancelar)
    // ====================================================
    public Pedido obtenerPedidoUsuario(Long pedidoId, Authentication auth) {
        Usuario usuario = getUsuario(auth);
        if (usuario == null) {
            throw new IllegalStateException("Debe iniciar sesión.");
        }

        return pedidoRepository.findById(pedidoId)
                .filter(p -> p.getUsuario().getId().equals(usuario.getId()))
                .orElseThrow(() -> new IllegalStateException("Pedido no encontrado."));
    }

    // ====================================================
    // CANCELAR PEDIDO (opcional)
    // ====================================================
    public void cancelarPedido(Long pedidoId, Authentication auth) {
        Pedido pedido = obtenerPedidoUsuario(pedidoId, auth);

        if (pedido.getEstado() == EstadoPedido.CANCELADO ||
            pedido.getEstado() == EstadoPedido.ENTREGADO) {
            throw new IllegalStateException("No se puede cancelar este pedido.");
        }

        pedido.setEstado(EstadoPedido.CANCELADO);
        // También marcamos el pago como CANCELADO
        pedido.setEstadoPago(EstadoPago.CANCELADO);

        pedidoRepository.save(pedido);
    }

    // ====================================================
    // MARCAR COMO ENTREGADO (pensado para admin/repartidor)
    // ====================================================
    public void marcarEntregado(Long pedidoId) {
        pedidoRepository.findById(pedidoId).ifPresent(p -> {
            if (p.getEstado() != EstadoPedido.CANCELADO) {
                p.setEstado(EstadoPedido.ENTREGADO);

                // Si es contraentrega y estaba pendiente, al entregar se considera PAGADO
                if (p.getMetodoPago() == MetodoPago.CONTRAENTREGA
                        && p.getEstadoPago() == EstadoPago.PENDIENTE) {
                    p.setEstadoPago(EstadoPago.PAGADO);
                }

                pedidoRepository.save(p);
            }
        });
    }

    // ====================================================
    // REPETIR PEDIDO -> vuelve a llenar el carrito
    // ====================================================
    public void repetirPedido(Long pedidoId, Authentication auth) {
        Pedido pedido = obtenerPedidoUsuario(pedidoId, auth);

        // Limpia carrito actual
        cartService.clear(auth);

        // Vuelve a meter los productos del pedido en el carrito
        for (PedidoDetalle det : pedido.getDetalles()) {

            int cantidad = det.getCantidad();
            if (cantidad < 1) {
                cantidad = 1;
            }

            // CartService.addItem recibe el precio como String
            String precioStr = det.getPrecioUnitario() != null
                    ? det.getPrecioUnitario().toPlainString()
                    : "0";

            cartService.addItem(
                    auth,
                    det.getCodigoProducto(),
                    det.getNombreProducto(),
                    precioStr,
                    det.getImagenUrl(),
                    det.getServicio(),
                    cantidad
            );
        }
    }

    // ====================================================
    // HELPER PRIVADO: OBTENER USUARIO AUTENTICADO
    // ====================================================
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
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    // ================== REPARTIDOR ==================

    // Cambiar estado genérico (PENDIENTE → EN_CAMINO, EN_CAMINO → ENTREGADO, etc.)
    public void cambiarEstado(Long pedidoId, EstadoPedido nuevoEstado) {
        pedidoRepository.findById(pedidoId).ifPresent(p -> {
            p.setEstado(nuevoEstado);

            // Si desde aquí lo pasan a ENTREGADO y es contraentrega, marcamos pago como PAGADO
            if (nuevoEstado == EstadoPedido.ENTREGADO
                    && p.getMetodoPago() == MetodoPago.CONTRAENTREGA
                    && p.getEstadoPago() == EstadoPago.PENDIENTE) {
                p.setEstadoPago(EstadoPago.PAGADO);
            }

            pedidoRepository.save(p);
        });
    }

    // Obtener pedido por ID (para detalle del repartidor)
    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Pedido no encontrado"));
    }

    // ✅ Pedidos pendientes para el rol REPARTIDOR
    public List<Pedido> listarPedidosPendientes() {
        return pedidoRepository
                .findByEstadoOrderByFechaCreacionAsc(EstadoPedido.PENDIENTE);
    }

    // ✅ Pedidos EN CAMINO
    public List<Pedido> listarPedidosEnCamino() {
        return pedidoRepository
                .findByEstadoOrderByFechaCreacionAsc(EstadoPedido.EN_CAMINO);
    }

    // ✅ Pedidos EN ESPERA (aquí uso PAGO_PENDIENTE como "en espera")
    public List<Pedido> listarPedidosEnEspera() {
        return pedidoRepository
                .findByEstadoOrderByFechaCreacionAsc(EstadoPedido.PAGO_PENDIENTE);
    }

    public void eliminarPedidoUsuario(Long pedidoId, Authentication auth) {
        Usuario usuario = getUsuario(auth);
        if (usuario == null) {
            throw new IllegalStateException("Debe iniciar sesión.");
        }

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .filter(p -> p.getUsuario().getId().equals(usuario.getId()))
                .orElseThrow(() -> new IllegalStateException("Pedido no encontrado."));

        if (pedido.getEstado() != EstadoPedido.ENTREGADO &&
            pedido.getEstado() != EstadoPedido.CANCELADO) {
            throw new IllegalStateException("Solo puedes eliminar pedidos entregados o cancelados.");
        }

        pedidoRepository.delete(pedido);
    }

    // ✅ Contar pedidos activos del usuario (para el badge del navbar)
    public int contarPedidosActivos(Authentication auth) {
        Usuario usuario = getUsuario(auth);
        if (usuario == null) return 0;

        List<EstadoPedido> activos = List.of(
                EstadoPedido.PENDIENTE,
                EstadoPedido.PAGO_PENDIENTE,
                EstadoPedido.PAGADO,
                EstadoPedido.EN_CAMINO
        );

        return pedidoRepository.countByUsuarioAndEstadoIn(usuario, activos);
    }

    // ✅ Pedidos ENTREGADOS
public List<Pedido> listarPedidosEntregados() {
    return pedidoRepository
            .findByEstadoOrderByFechaCreacionAsc(EstadoPedido.ENTREGADO);
}

// ✅ Pedidos CANCELADOS
public List<Pedido> listarPedidosCancelados() {
    return pedidoRepository
            .findByEstadoOrderByFechaCreacionAsc(EstadoPedido.CANCELADO);
}
 // ✅ NUEVO: para admin listar todos
    public List<Pedido> listarTodosAdmin() {
        return pedidoRepository.findAllByOrderByFechaCreacionDesc();
    }

}
