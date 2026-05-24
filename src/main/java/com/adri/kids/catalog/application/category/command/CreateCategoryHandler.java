package com.adri.kids.catalog.application.category.command;

import an.awesome.pipelinr.Command;

import com.adri.kids.catalog.domain.model.Category;
import com.adri.kids.catalog.domain.port.out.CategoryRepositoryPort;
import com.adri.kids.shared.exceptions.AlreadyExistException;
import com.adri.kids.shared.utils.ConstantUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateCategoryHandler implements Command.Handler<CreateCategoryCommand, Category> {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Transactional(timeout = ConstantUtil.TIME_OUT_TRANSACTION, isolation = Isolation.READ_COMMITTED)
    @Override
    public Category handle(CreateCategoryCommand command) {

        this.validateAlreadyExistNameForCategory(command.name());

        var category = Category.createCategory(
                command.name(),
                command.slug(),
                command.descriptionShort(),
                command.descriptionLong(),
                command.urlImage(),
                command.numberOfOrder(),
                command.colorCode(),
                command.isShowMainMenu());

        return categoryRepositoryPort.save(category);
    }

    private void validateAlreadyExistNameForCategory(String name) {
        if (categoryRepositoryPort.existsByName(name)) {
            throw new AlreadyExistException("El nombre de categoría ya existe: " + name);
        }
    }
}
