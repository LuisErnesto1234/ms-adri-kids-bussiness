package com.adri.kids.inventory.domain.port.out;

import com.adri.kids.inventory.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepositoryPort {
    Category save(Category category);

    Optional<Category> findById(UUID id);

    Optional<Category> findByName(String name);

    Page<Category> findAll(Pageable pageable);
}
