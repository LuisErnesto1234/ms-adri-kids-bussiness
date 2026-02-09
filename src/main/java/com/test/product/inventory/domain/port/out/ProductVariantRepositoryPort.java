package com.test.product.inventory.domain.port.out;

import com.test.product.inventory.domain.model.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ProductVariantRepositoryPort {
    ProductVariant save(ProductVariant productVariant);

    Optional<ProductVariant> findById(UUID id);

    Boolean existById(UUID id);

    Page<ProductVariant> findAll(Pageable pageable);

}
