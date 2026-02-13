package com.test.product.inventory.application.querys.getproduct;

import an.awesome.pipelinr.Command;

import com.test.product.inventory.domain.port.out.ProductRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.product.ProductCardResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.ProductRestMapper;
import com.test.product.shared.domain.dtos.PagedResult;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetProductsHandler implements Command.Handler<GetProductsQuery, PagedResult<ProductCardResponse>> {

    private final ProductRepositoryPort productRepositoryPort;
    private final ProductRestMapper productRestMapper;

    @Transactional(readOnly = true, timeout = 10, isolation = Isolation.READ_COMMITTED)
    @Cacheable(
            value = "products_page",
            key = "'products_page' + #query.pageable().pageNumber + '-' + #query.pageable().pageSize + '-' + (#query.filterText ?: 'empty') + '-' + #query.pageable().sort",
            unless = "#result.content.empty"
    )
    @Override
    public PagedResult<ProductCardResponse> handle(GetProductsQuery query) {

        var domainPage = productRepositoryPort.findAllWithCategory(query.pageable());

        ArrayList<ProductCardResponse> mutableContent = domainPage.getContent().stream()
                .map(productRestMapper::toResponseCard)
                .collect(Collectors.toCollection(ArrayList::new));

        return new PagedResult<>(
                mutableContent,
                query.pageable().getPageNumber(),
                query.pageable().getPageSize(),
                domainPage.getTotalElements(),
                domainPage.getTotalPages()
        );
    }
}
