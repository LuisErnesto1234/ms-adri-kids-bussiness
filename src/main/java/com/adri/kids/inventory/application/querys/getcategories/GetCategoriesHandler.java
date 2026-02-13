package com.adri.kids.inventory.application.querys.getcategories;

import an.awesome.pipelinr.Command;

import com.adri.kids.inventory.domain.port.out.CategoryRepositoryPort;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.category.CategoryCardResponse;
import com.adri.kids.inventory.infrastructure.adapter.in.mapper.CategoryRestMapper;
import com.adri.kids.shared.domain.dtos.PagedResult;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetCategoriesHandler implements Command.Handler<GetCategoriesQuery,
        PagedResult<CategoryCardResponse>> {

    private final CategoryRepositoryPort categoryRepositoryPort;
    private final CategoryRestMapper categoryRestMapper;

    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, timeout = 10)
    @Override
    @Cacheable(
            value = "categories_page",
            key = "'category:' + #query.pageable().pageNumber + '-' + #query.pageable().pageSize + '-' + (#query.filterText ?: 'empty') + '-' + #query.pageable().sort",
            unless = "#result.content.empty"
    )
    public PagedResult<CategoryCardResponse> handle(GetCategoriesQuery query) {

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
