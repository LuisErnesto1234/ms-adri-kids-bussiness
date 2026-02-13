package com.adri.kids.inventory.infrastructure.adapter;

import com.adri.kids.inventory.domain.model.Category;
import com.adri.kids.inventory.domain.port.out.CategoryRepositoryPort;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.entity.CategoryEntity;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.mapper.CategoryEntityMapper;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryJpaRepository.findAll(pageable)
                .map(categoryEntityMapper::toDomain);
    }
}
