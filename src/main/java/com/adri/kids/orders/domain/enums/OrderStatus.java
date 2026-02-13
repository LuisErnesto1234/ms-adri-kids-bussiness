package com.adri.kids.orders.domain.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING_PAYMENT("Pending Payment"), // Creada, esperando a la pasarela
    PAID("Paid"),            // Pago confirmado, descontar stock final
    PREPARING("Preparing"),       // Almacén empaquetando
    SHIPPED("Shipped"),         // Entregado al courier
    DELIVERED("Delivered"),       // Cliente feliz
    CANCELLED("Cancelled"),// Algo salió mal
    PENDING("Pending"),         // Estado inicial, esperando confirmación
    RETURNED("Returned"),        // Producto devuelto, esperando confirmación
    REFUNDED("Refunded"),         // Dinero devuelto al cliente
    RETURN_REQUESTED("Return Requested"), // Cliente solicita devolución
    COMPLETED("Completed"); // Pedido finalizado

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

}
