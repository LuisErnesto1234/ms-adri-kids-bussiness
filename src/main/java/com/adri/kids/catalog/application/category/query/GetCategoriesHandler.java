package com.adri.kids.catalog.application.category.query;

import an.awesome.pipelinr.Command;

import com.adri.kids.catalog.domain.model.Category;
import com.adri.kids.catalog.domain.port.out.CategoryRepositoryPort;
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
