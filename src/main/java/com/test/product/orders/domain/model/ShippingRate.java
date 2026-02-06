package com.test.product.orders.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

import java.util.UUID;

/**
 * Representa una tarifa de envío estándar administrada por el sistema.
 * Estas tarifas se utilizan para calcular el costo de envío de una orden
 * basado en el origen y el destino.
 *
 * @param originState          Ciudad de origen del envío.
 * @param destinationState     Departamento/Provincia de destino.
 * @param destinationCity      Ciudad de destino.
 * @param cost                 El costo de esta ruta de envío.
 * @param estimateDeliveryDays Días estimados para la entrega (opcional).
 */
public record ShippingRate(UUID id, String originState, String destinationState, String destinationCity,
                           BigDecimal cost,
                           Long estimateDeliveryDays, Instant dateEstimateDelivery, Instant createdAt,
                           Instant updatedAt) {

    public ShippingRate {
        if (originState == null || destinationState == null) {
            throw new IllegalArgumentException("Origin and destination states cannot be null");
        }

        if (cost == null || cost.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El costo del precio del envio no puede ser nulo, o negativo");
        }
    }

    public static ShippingRate create(String originState, String destinationState, String destinationCity, BigDecimal cost, Long estimateDeliveryDays) {

        return new ShippingRate(UUID.randomUUID(), originState, destinationState, destinationCity, cost, estimateDeliveryDays,
                Instant.now(), Instant.now(), Instant.now());
    }

    public ShippingRate update(UUID id, String originState, String destinationState, String destinationCity, BigDecimal cost, Long estimateDeliveryDays) {
        return new ShippingRate(id, originState, destinationState, destinationCity, cost, estimateDeliveryDays, this.dateEstimateDelivery, this.createdAt, Instant.now());
    }

    public Long calculateDaysDeliveryEstimated() {
        Temporal originDate = Instant.now();
        return ChronoUnit.DAYS.between(originDate, this.dateEstimateDelivery);
    }
}
