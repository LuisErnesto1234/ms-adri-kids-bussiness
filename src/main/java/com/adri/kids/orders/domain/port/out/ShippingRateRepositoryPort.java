package com.adri.kids.orders.domain.port.out;

import com.adri.kids.orders.domain.model.ShippingRate;

import java.util.Optional;
import java.util.UUID;

public interface ShippingRateRepositoryPort {
    Optional<ShippingRate> findById(UUID id);

    ShippingRate save(ShippingRate shippingRate);

    void delete(ShippingRate shippingRate);
}