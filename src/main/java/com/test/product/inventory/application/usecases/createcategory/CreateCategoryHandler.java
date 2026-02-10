package com.test.product.inventory.application.usecases.createcategory;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.model.Category;
import com.test.product.inventory.domain.port.out.CategoryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCategoryHandler implements Command.Handler<CreateCategoryCommand, Category> {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public Category handle(CreateCategoryCommand command) {
        Category category = Category.createCategory(command.name(), command.description(),
                command.urlImage(), command.status());

        return categoryRepositoryPort.save(category);
    }
}
