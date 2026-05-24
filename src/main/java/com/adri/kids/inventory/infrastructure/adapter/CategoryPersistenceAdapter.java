package com.adri.kids.inventory.infrastructure.adapter;

import com.adri.kids.inventory.domain.model.Category;
import com.adri.kids.inventory.domain.port.out.CategoryRepositoryPort;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.request.category.filter.CategoryFilterRequest;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.mapper.CategoryEntityMapper;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.repository.CategoryJpaRepository;
import com.adri.kids.inventory.infrastructure.adapter.out.persistence.specification.CategorySpecification;
import com.adri.kids.shared.exceptions.NotFoundException;

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
        var categoryEntity = categoryEntityMapper.toEntity(category);
        var categorySaved = categoryJpaRepository.save(categoryEntity);

        return categoryEntityMapper.toDomain(categorySaved);
    }

    @Override
    public Optional<Category> findById(UUID id) {
        return categoryJpaRepository.findById(id).map(categoryEntityMapper::toDomain);
    }

    @Override
    public Category findByIdOrThrow(UUID id) {
        return categoryJpaRepository.findById(id)
                .map(categoryEntityMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con id: " + id));
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryJpaRepository.findAll(pageable)
                .map(categoryEntityMapper::toDomain);
    }

    @Override
    public boolean existsByName(String name) {
        return categoryJpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, UUID id) {
        return categoryJpaRepository.existsByNameAndIdNot(name, id);
    }

    @Override
    public void deleteById(UUID id) {
        categoryJpaRepository.deleteById(id);
    }

    @Override
    public Page<Category> findAllByFilter(Pageable pageable, CategoryFilterRequest filterRequest) {
        var categorySpecification = CategorySpecification.byFilter(filterRequest);
        return categoryJpaRepository.findAll(categorySpecification, pageable)
                .map(categoryEntityMapper::toDomain);
    }
}
