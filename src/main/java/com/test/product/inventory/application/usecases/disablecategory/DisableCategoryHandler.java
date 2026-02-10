package com.test.product.inventory.application.usecases.disablecategory;

import an.awesome.pipelinr.Command;

import com.test.product.inventory.domain.port.out.CategoryRepositoryPort;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DisableCategoryHandler implements Command.Handler<DisableCategoryCommand, Void> {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Transactional(readOnly = true)
    @CacheEvict(value = "categories_page", key = "#command.categoryId()")
    @Override
    public Void handle(DisableCategoryCommand command) {

        var categoryFind = categoryRepositoryPort.findById(command.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryFind.disable();
        categoryRepositoryPort.save(categoryFind);

        return null;
    }
}
