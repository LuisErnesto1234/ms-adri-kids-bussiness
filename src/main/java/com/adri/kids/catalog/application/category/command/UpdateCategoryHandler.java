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

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateCategoryHandler implements Command.Handler<UpdateCategoryCommand, Category> {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Transactional(
            timeout = ConstantUtil.TIME_OUT_TRANSACTION,
            isolation = Isolation.REPEATABLE_READ
    )
    @Override
    public Category handle(UpdateCategoryCommand command) {
        var categoryFound = categoryRepositoryPort.findByIdOrThrow(command.categoryId());

        validateAlreadyExistName(command.name(), command.categoryId());

        var categoryUpdate = categoryFound.updateCategory(
                command.name(),
                command.descriptionShort(),
                command.descriptionLong(),
                command.urlImage(),
                command.numberOfOrder(),
                command.colorCode(),
                command.isShowMainMenu(),
                command.status()
        );

        categoryRepositoryPort.save(categoryUpdate);

        return categoryUpdate;
    }

    private void validateAlreadyExistName(String name, UUID categoryId) {
        if (categoryRepositoryPort.existsByNameAndIdNot(name, categoryId)) {
            throw new AlreadyExistException("El nombre ya esta registrado para otra categoría.");
        }
    }
}
