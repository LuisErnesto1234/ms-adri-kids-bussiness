package com.adri.kids.inventory.infrastructure.adapter;

import com.adri.kids.inventory.domain.model.Product;
import com.adri.kids.inventory.domain.model.details.ProductDetails;
import com.adri.kids.inventory.domain.port.out.ProductRepositoryPort;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.entity.ProductEntity;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.mapper.ProductEntityMapper;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.repository.ProductJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
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

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productJpaRepository.findAll(pageable).map(productEntityMapper::toDomain);
    }

    @Override
    public Page<ProductDetails> findAllWithCategory(Pageable pageable) {
        Page<ProductEntity> entities = productJpaRepository.findAllWithCategory(pageable);

        return entities.map(productEntityMapper::toDetails);
    }

    @Override
    public List<Product> findAllByCategory(UUID categoryId) {
        return productJpaRepository.findAllByCategoryId(categoryId)
                .stream().map(productEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDetails> findProductDetailsById(UUID id) {
        return productJpaRepository.findProductDetailsById(id)
                .map(productEntityMapper::toDetails);
    }
}
