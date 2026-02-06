package com.test.product.orders.domain.port.out;

import com.test.product.orders.domain.model.ShippingRate;

import java.util.Optional;
import java.util.UUID;

public interface ShippingRateRepositoryPort {
    Optional<ShippingRate> findById(UUID id);

    ShippingRate save(ShippingRate shippingRate);

    void delete(ShippingRate shippingRate);
}