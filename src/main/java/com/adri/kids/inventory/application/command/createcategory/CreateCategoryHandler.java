package com.adri.kids.inventory.application.command.createcategory;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.domain.model.Category;
import com.adri.kids.inventory.domain.port.out.CategoryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateCategoryHandler implements Command.Handler<CreateCategoryCommand, Category> {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Transactional(rollbackFor = Exception.class, timeout = 15, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @CacheEvict(value = "categories_page", allEntries = true)
    @Override
    public Category handle(CreateCategoryCommand command) {
        Category category = Category.createCategory(command.name(), command.description(),
                command.urlImage(), command.status());

        return categoryRepositoryPort.save(category);
    }
}
