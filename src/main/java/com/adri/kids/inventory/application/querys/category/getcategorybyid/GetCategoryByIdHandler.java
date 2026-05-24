package com.adri.kids.inventory.application.querys.category.getcategorybyid;

import an.awesome.pipelinr.Command;

import com.adri.kids.inventory.domain.model.Category;
import com.adri.kids.inventory.domain.port.out.CategoryRepositoryPort;

import com.adri.kids.shared.utils.ConstantUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetCategoryByIdHandler implements Command.Handler<GetCategoryByIdQuery, Category> {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Transactional(
            readOnly = true,
            isolation = Isolation.REPEATABLE_READ,
            timeout = ConstantUtil.TIME_OUT_TRANSACTION
    )
    @Override
    public Category handle(GetCategoryByIdQuery query) {
        return categoryRepositoryPort.findByIdOrThrow(query.categoryId());
    }

}
