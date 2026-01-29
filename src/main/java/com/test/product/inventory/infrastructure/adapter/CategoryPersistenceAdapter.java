package com.test.product.inventory.infrastructure.adapter;

import com.test.product.inventory.domain.model.Category;
import com.test.product.inventory.domain.port.out.CategoryRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.out.persistence.entity.CategoryEntity;
import com.test.product.inventory.infrastructure.adapter.out.persistence.mapper.CategoryEntityMapper;
import com.test.product.inventory.infrastructure.adapter.out.persistence.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements CategoryRepositoryPort {

    private final CategoryJpaRepository categoryJpaRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public Category save(Category category) {
        CategoryEntity categoryEntity = categoryJpaRepository.save(categoryEntityMapper.toEntity(category));

        return categoryEntityMapper.toDomain(categoryEntity);
    }

    @Override
    public Optional<Category> findById(UUID id) {
        return categoryJpaRepository.findById(id).map(categoryEntityMapper::toDomain);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryJpaRepository.findByName(name).map(categoryEntityMapper::toDomain);
    }
}
