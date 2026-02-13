package com.adri.kids.orders.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItem(UUID id, UUID variantId,
                        int quantity,
                        BigDecimal unitPrice, BigDecimal subTotal) {

    public static OrderItem create(UUID variantId, int quantity, BigDecimal unitPrice) {
        BigDecimal subTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        return new OrderItem(UUID.randomUUID(), variantId, quantity, unitPrice, subTotal);
    }

    public OrderItem withQuantity(int newQuantity) {
        BigDecimal newSubTotal = this.unitPrice.multiply(BigDecimal.valueOf(newQuantity));
        return new OrderItem(
                this.id,        // MANTENEMOS EL ID ORIGINAL
                this.variantId,
                newQuantity,
                this.unitPrice,
                newSubTotal     // Guardamos el subtotal pre-calculado
        );
    }
}
