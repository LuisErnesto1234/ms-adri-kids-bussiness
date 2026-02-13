package com.adri.kids.orders.infrastructure.adapter;

import com.adri.kids.orders.domain.model.ShippingRate;
import com.adri.kids.orders.domain.model.exeception.OrderItemNotFoundException;
import com.adri.kids.orders.domain.port.out.ShippingRateRepositoryPort;
import com.adri.kids.orders.infrastructure.adapter.out.persistence.mapper.ShippingRateEntityMapper;
import com.adri.kids.orders.infrastructure.adapter.out.persistence.repository.ShippingRateJpaRepository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ShippingRatePersistenceAdapter implements ShippingRateRepositoryPort {

    private final ShippingRateJpaRepository shippingRateJpaRepository;
    private final ShippingRateEntityMapper shippingRateEntityMapper;

    @Override
    public Optional<ShippingRate> findById(UUID id) {
        return shippingRateJpaRepository.findById(id)
                .map(shippingRateEntityMapper::toDomain);
    }

    @Override
    public ShippingRate save(ShippingRate shippingRate) {
        var shippingEntity = shippingRateEntityMapper.toEntity(shippingRate);

        var shippingEntitySaved = shippingRateJpaRepository.save(shippingEntity);

        return shippingRateEntityMapper.toDomain(shippingEntitySaved);
    }

    @Override
    public void delete(ShippingRate shippingRate) {
        if (shippingRateJpaRepository.existsById(shippingRate.id())){
            var shippingRateToDelete = shippingRateEntityMapper.toEntity(shippingRate);
            shippingRateJpaRepository.delete(shippingRateToDelete);
            return;
        }

        throw new OrderItemNotFoundException("El Costo de envio no fue econtrado para su eliminacion");
    }
}
