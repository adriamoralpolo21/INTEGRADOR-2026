package com.glovo.app.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.glovo.app.entity.MetodoPago;
import com.glovo.app.entity.EstadoPago;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usuario dueño del pedido
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "direccion_entrega", nullable = false, length = 255)
    private String direccionEntrega;

    @Column(name = "referencia", length = 255)
    private String referencia;

       @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", length = 30)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private EstadoPedido estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago", length = 20, nullable = false)
    private EstadoPago estadoPago;
    

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @Column(name = "costo_envio", precision = 10, scale = 2, nullable = false)
    private BigDecimal costoEnvio;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal total;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoDetalle> detalles = new ArrayList<>();

            @PrePersist
    public void prePersist() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
        if (estado == null) {
            estado = EstadoPedido.PENDIENTE;
        }
        if (estadoPago == null) {
            estadoPago = EstadoPago.PENDIENTE;
        }
    }
    

    // ===== getters y setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

       public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public EstadoPago getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(BigDecimal costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<PedidoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PedidoDetalle> detalles) {
        this.detalles = detalles;
    }

    public void addDetalle(PedidoDetalle detalle) {
        detalle.setPedido(this);
        this.detalles.add(detalle);
    }
}
