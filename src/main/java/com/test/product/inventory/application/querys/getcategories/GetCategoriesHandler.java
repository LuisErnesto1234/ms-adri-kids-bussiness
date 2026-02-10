package com.test.product.inventory.application.querys.getcategories;

import an.awesome.pipelinr.Command;

import com.test.product.inventory.domain.port.out.CategoryRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.CategorySummaryResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.CategoryRestMapper;
import com.test.product.shared.domain.dtos.PagedResult;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
            key = "#query.pageable().pageNumber + '-' + #query.pageable().pageSize + '-' + (#query.filterText() ?: 'empty')",
            unless = "#result.content().empty"
    )
    public PagedResult<CategorySummaryResponse> handle(GetCategoriesQuery query) {

        var domainPage = categoryRepositoryPort.findAll(query.pageable());

        var categoriesMutable = domainPage.stream()
                .map(categoryRestMapper::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));

        return new PagedResult<>(
                categoriesMutable,
                query.pageable().getPageNumber(),
                query.pageable().getPageSize(),
                domainPage.getTotalElements(),
                domainPage.getTotalPages()
        );
    }
}
