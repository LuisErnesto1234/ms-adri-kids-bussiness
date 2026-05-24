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
public class DeactivateCategoryHandler implements Command.Handler<DeactivateCategoryCommand, Category> {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            timeout = ConstantUtil.TIME_OUT_TRANSACTION
    )
    @Override
    public Category handle(DeactivateCategoryCommand command) {
        var categoryFound = categoryRepositoryPort.findByIdOrThrow(command.categoryId());

        var categoryUpdate = categoryFound.deactivate();

        return categoryRepositoryPort.save(categoryUpdate);
    }
}
