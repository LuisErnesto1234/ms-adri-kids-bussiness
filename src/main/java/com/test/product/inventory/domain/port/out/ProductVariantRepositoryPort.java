package com.test.product.inventory.domain.port.out;

import com.test.product.inventory.domain.model.ProductVariant;

import java.util.Optional;
import java.util.UUID;

public interface ProductVariantRepositoryPort {
    ProductVariant save(ProductVariant productVariant);

    Optional<ProductVariant> findById(UUID id);

    Boolean existById(UUID id);

}
