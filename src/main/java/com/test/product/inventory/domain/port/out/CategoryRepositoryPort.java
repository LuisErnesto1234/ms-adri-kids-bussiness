package com.test.product.inventory.domain.port.out;

import com.test.product.inventory.domain.model.Category;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepositoryPort {
    Category save(Category category);

    Optional<Category> findById(UUID id);

    Optional<Category> findByName(String name);
}
