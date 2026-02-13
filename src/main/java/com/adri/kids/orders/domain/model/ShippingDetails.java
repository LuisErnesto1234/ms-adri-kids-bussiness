package com.adri.kids.orders.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public record ShippingDetails(
        Address address,           // Snapshot de la dirección
        String carrier,            // Ej: "DHL", "Olva Courier"
        String trackingCode,       // Ej: "PE-123456789"
        BigDecimal cost,           // Cuánto costó el envío
        Instant estimatedDeliveryDate
) {

    public ShippingDetails{
        if (address == null){
            throw new IllegalArgumentException("Shipping address cannot be null");
        }
    }

    public static ShippingDetails createPending(Address address, BigDecimal cost) {
        return new ShippingDetails(address, null, null, cost, null);
    }


    // Wither para cuando se crea el envío pendiente (cuando recién crean la orden)
    public ShippingDetails pending(Address address, BigDecimal cost) {
        return new ShippingDetails(address, null, null, cost, null);
    }

    // Wither para cuando despachas el producto
    public ShippingDetails markAsShipped(String carrier, String trackingCode) {
        return new ShippingDetails(
                this.address,
                carrier,
                trackingCode,
                this.cost,
                Instant.now().plusSeconds(86400L * 3L) // +3 días estimado
        );
    }

    // Wither para cuando se actualiza la fecha de entrega
    public ShippingDetails updateEstimatedDeliveryDate(Instant newEstimatedDeliveryDate) {
        return new ShippingDetails(this.address, this.carrier, this.trackingCode, this.cost, newEstimatedDeliveryDate);
    }

    // Wither para cuando se actualiza el costo de envío
    public ShippingDetails updateCost(BigDecimal newCost) {
        return new ShippingDetails(this.address, this.carrier, this.trackingCode, newCost, this.estimatedDeliveryDate);
    }
}
