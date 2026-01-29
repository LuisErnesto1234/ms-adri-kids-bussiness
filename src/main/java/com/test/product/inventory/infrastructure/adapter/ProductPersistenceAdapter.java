package com.test.product.inventory.infrastructure.adapter;

import com.test.product.inventory.domain.model.Product;
import com.test.product.inventory.domain.port.out.ProductRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.ProductEntity;
import com.test.product.inventory.infrastructure.adapter.out.persistence.mapper.ProductEntityMapper;
import com.test.product.inventory.infrastructure.adapter.out.persistence.repository.ProductJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductRepositoryPort {

    private final ProductJpaRepository productJpaRepository;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public Product save(Product product) {
        ProductEntity productSave = productJpaRepository.save(productEntityMapper.toEntity(product));
        return productEntityMapper.toDomain(productSave);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productJpaRepository.findById(id)
                .map(productEntityMapper::toDomain);
    }

    @Override
    public Boolean existById(UUID id) {
        return productJpaRepository.existsById(id);
    }
}
