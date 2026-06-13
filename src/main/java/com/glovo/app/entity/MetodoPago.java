package com.glovo.app.entity;

public enum MetodoPago {

    TARJETA("tarjeta"),
    YAPE("yape"),
    CONTRAENTREGA("contraentrega");

    private final String codigo;

    MetodoPago(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static MetodoPago fromCodigo(String codigo) {
        if (codigo == null) return null;
        String val = codigo.toLowerCase();
        for (MetodoPago m : values()) {
            if (m.codigo.equals(val)) {
                return m;
            }
        }
        throw new IllegalArgumentException("Método de pago no soportado: " + codigo);
    }
}
