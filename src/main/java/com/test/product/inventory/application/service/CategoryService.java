package com.test.product.inventory.application.service;

import com.test.product.inventory.domain.model.Category;
import com.test.product.inventory.domain.port.in.category_use_case.CreateCategoryUseCase;
import com.test.product.inventory.domain.port.in.command.category.CreateCategoryCommand;
import com.test.product.inventory.domain.port.out.CategoryRepositoryPort;

public class CategoryService implements CreateCategoryUseCase {

    private final CategoryRepositoryPort categoryRepositoryPort;

    public CategoryService(CategoryRepositoryPort categoryRepositoryPort) {
        this.categoryRepositoryPort = categoryRepositoryPort;
    }

    @Override
    public Category createCategory(CreateCategoryCommand command) {

        Category category = Category.createCategory(command.name(), command.description(), command.urlImage());

        return categoryRepositoryPort.save(category);
    }
}
