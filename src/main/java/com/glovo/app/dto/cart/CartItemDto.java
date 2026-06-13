package com.glovo.app.dto.cart;

import java.math.BigDecimal;

public class CartItemDto {

    private String id;
    private String nombre;
    private BigDecimal precio;
    private int cantidad;
    private String imagenUrl;
    private String servicio;

    // --- único getSubtotal ---
    public BigDecimal getSubtotal() {
        if (precio == null) return BigDecimal.ZERO;
        return precio.multiply(BigDecimal.valueOf(cantidad));
    }

    // --- getters / setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
}
