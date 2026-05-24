package com.adri.kids.catalog.application.category.command;

import an.awesome.pipelinr.Command;

import com.adri.kids.catalog.domain.model.Category;
import com.adri.kids.catalog.domain.port.out.CategoryRepositoryPort;
import com.adri.kids.shared.utils.ConstantUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ActivateCategoryHandler implements Command.Handler<ActivateCategoryCommand, Category> {

    private final CategoryRepositoryPort categoryRepositoryPort;


    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            timeout = ConstantUtil.TIME_OUT_TRANSACTION
    )
    @Override
    public Category handle(ActivateCategoryCommand command) {
        var categoryFound = categoryRepositoryPort.findByIdOrThrow(command.categoryId());

        var categoryUpdate = categoryFound.activate();

        categoryRepositoryPort.save(categoryUpdate);

        return categoryUpdate;
    }
}
