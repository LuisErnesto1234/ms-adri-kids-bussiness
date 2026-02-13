package com.adri.kids.inventory.infrastructure.adapter;

import com.adri.kids.inventory.domain.model.ProductVariant;
import com.adri.kids.inventory.domain.model.details.ProductVariantDetails;
import com.adri.kids.inventory.domain.port.out.ProductVariantRepositoryPort;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.entity.ProductVariantEntity;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.mapper.ProductVariantEntityMapper;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.repository.ProductVariantJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductVariantPersistenceAdapter implements ProductVariantRepositoryPort {

    private final ProductVariantJpaRepository productVariantJpaRepository;
    private final ProductVariantEntityMapper productVariantEntityMapper;

    @Override
    public ProductVariant save(ProductVariant productVariant) {
        ProductVariantEntity productVariantEntity = productVariantEntityMapper.toEntity(productVariant);
        productVariantJpaRepository.save(productVariantEntity);
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

    @Override
    public Page<ProductVariant> findAll(Pageable pageable) {
        return productVariantJpaRepository.findAllProductVariantWithColorWithSizes(pageable)
                .map(productVariantEntityMapper::toDomain);
    }

    @Override
    public Optional<ProductVariantDetails> findProductVariantDetailById(UUID id) {
        return productVariantJpaRepository.findById(id)
                .map(productVariantEntityMapper::toDetails);
    }
}
