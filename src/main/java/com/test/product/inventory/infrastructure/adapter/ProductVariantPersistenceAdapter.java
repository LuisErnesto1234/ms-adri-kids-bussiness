package com.test.product.inventory.infrastructure.adapter;

import com.test.product.inventory.domain.model.ProductVariant;
import com.test.product.inventory.domain.port.out.ProductVariantRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.ProductVariantEntity;
import com.test.product.inventory.infrastructure.adapter.out.persistence.mapper.ProductVariantEntityMapper;
import com.test.product.inventory.infrastructure.adapter.out.persistence.repository.ProductVariantJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductVariantPersistenceAdapter implements ProductVariantRepositoryPort {

    private final ProductVariantJpaRepository productVariantJpaRepository;
    private final ProductVariantEntityMapper productVariantEntityMapper;

    @Override
    public ProductVariant save(ProductVariant productVariant) {
        ProductVariantEntity productVariantEntity = productVariantJpaRepository.save(productVariantEntityMapper.toEntity(productVariant));

        return productVariantEntityMapper.toDomain(productVariantEntity);
    }

    @Override
    public Optional<ProductVariant> findById(UUID id) {
        return productVariantJpaRepository.findByIdWithLock(id)
                .map(productVariantEntityMapper::toDomain);
    }

    @Override
    public Boolean existById(UUID id) {
        return productVariantJpaRepository.existsById(id);
    }
}
