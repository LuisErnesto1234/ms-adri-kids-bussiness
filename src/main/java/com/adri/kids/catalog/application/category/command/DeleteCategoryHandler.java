package com.adri.kids.catalog.application.category.command;

import an.awesome.pipelinr.Command;

import com.adri.kids.catalog.domain.port.out.CategoryRepositoryPort;
import com.adri.kids.shared.utils.ConstantUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteCategoryHandler implements Command.Handler<DeleteCategoryCommand, Void> {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            timeout = ConstantUtil.TIME_OUT_TRANSACTION
    )
    @Override
    public Void handle(DeleteCategoryCommand command) {
        var categoryFound = categoryRepositoryPort.findByIdOrThrow(command.categoryId());
        categoryRepositoryPort.deleteById(categoryFound.id());

        return null;
    }
}
