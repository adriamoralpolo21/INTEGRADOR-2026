package com.glovo.app.entity;

public enum EstadoPedido {
    PENDIENTE,        // creado, esperando pago / preparación
    PAGO_PENDIENTE,   // si el método lo requiere (yape, tarjeta)
    PAGADO,           // pago confirmado
    EN_CAMINO,        // repartidor lo va llevando
    ENTREGADO,        // completado
    CANCELADO         // cancelado por usuario o sistema
}
