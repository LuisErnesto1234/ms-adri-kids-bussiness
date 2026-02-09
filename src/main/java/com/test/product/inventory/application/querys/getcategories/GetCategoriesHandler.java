package com.test.product.inventory.application.querys.getcategories;

import an.awesome.pipelinr.Command;

import com.test.product.inventory.domain.port.out.CategoryRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.CategorySummaryResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.CategoryRestMapper;
import com.test.product.shared.domain.PagedResult;
import com.test.product.shared.mapper.PageMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetCategoriesHandler implements Command.Handler<GetCategoriesQuery,
        PagedResult<CategorySummaryResponse>> {

    private final CategoryRepositoryPort categoryRepositoryPort;
    private final CategoryRestMapper categoryRestMapper;

    @Transactional(readOnly = true)
    @Override
    @Cacheable(
            value = "categories_page",
            key = "#query.pageable().pageNumber + '-' + #query.pageable().pageSize + '-' + #query.filterText()",
            unless = "#result.isEmpty()" // No cachear si la lista está vacía
    )
    public PagedResult<CategorySummaryResponse> handle(GetCategoriesQuery query) {

        var categories = categoryRepositoryPort.findAll(query.pageable());

        var categoriesSummary = categories.map(categoryRestMapper::toResponse);

        return PageMapper.fromPage(categoriesSummary);
    }
}
