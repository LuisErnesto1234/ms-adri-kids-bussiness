package com.test.product.inventory.application.usecases.disablecategory;

import an.awesome.pipelinr.Command;

import com.test.product.inventory.domain.port.out.CategoryRepositoryPort;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DisableCategoryHandler implements Command.Handler<DisableCategoryCommand, UUID> {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, timeout = 10)
    @Caching(evict = {
            @CacheEvict(value = "categories_page", allEntries = true),
            @CacheEvict(value = "category", key = "'category:' + #result")
    })
    @Override
    public UUID handle(DisableCategoryCommand command) {
        var categoryFind = categoryRepositoryPort.findById(command.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryFind.disable();
        var savedCategory = categoryRepositoryPort.save(categoryFind);

        return savedCategory.id();
    }
}
