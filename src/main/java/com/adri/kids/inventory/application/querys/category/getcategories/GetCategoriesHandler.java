package com.adri.kids.inventory.application.querys.category.getcategories;

import an.awesome.pipelinr.Command;

import com.adri.kids.inventory.domain.model.Category;
import com.adri.kids.inventory.domain.port.out.CategoryRepositoryPort;
import com.adri.kids.shared.domain.dtos.PagedResult;

import com.adri.kids.shared.domain.mapper.PageMapper;

import com.adri.kids.shared.utils.ConstantUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetCategoriesHandler implements Command.Handler<GetCategoriesQuery,
        PagedResult<Category>> {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Transactional(
            readOnly = true,
            isolation = Isolation.READ_COMMITTED,
            timeout = ConstantUtil.TIME_OUT_TRANSACTION
    )
    @Override
    public PagedResult<Category> handle(GetCategoriesQuery query) {
        var pageable = Pageable.ofSize(query.size()).withPage(query.page());
        var categoryFoundsPage =
                categoryRepositoryPort.findAllByFilter(pageable, query.filterRequest());
        return PageMapper.fromPage(categoryFoundsPage);
    }
}
