package com.adri.kids.catalog.domain.port.out;

import com.adri.kids.catalog.domain.model.Category;
import com.adri.kids.catalog.infrastructure.adapter.in.dto.request.filter.CategoryFilterRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepositoryPort {
    Category save(Category category);

    Optional<Category> findById(UUID id);

    Category findByIdOrThrow(UUID id);

    Page<Category> findAll(Pageable pageable);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);

    void deleteById(UUID id);

    Page<Category> findAllByFilter(Pageable pageable, CategoryFilterRequest filterRequest);
}
