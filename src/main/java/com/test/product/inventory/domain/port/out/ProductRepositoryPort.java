package com.test.product.inventory.domain.port.out;

import com.test.product.inventory.domain.model.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepositoryPort {
    Product save(Product product);

    Optional<Product> findById(UUID id);

    Boolean existById(UUID id);
}
