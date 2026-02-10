package com.test.product.inventory.application.querys.getproduct;

import an.awesome.pipelinr.Command;

import com.test.product.inventory.domain.port.out.ProductRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ProductCardResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.ProductRestMapper;
import com.test.product.shared.domain.dtos.PagedResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetProductsHandler implements Command.Handler<GetProductsQuery, PagedResult<ProductCardResponse>> {

    private final ProductRepositoryPort productRepositoryPort;
    private final ProductRestMapper productRestMapper;

    @Transactional(readOnly = true)
    @Override
    @Cacheable(
            value = "products_page", // Nombre de la "tabla" en Redis
            key = "#query.pageable().pageNumber + '-' + #query.pageable().pageSize + '-' + (#query.filterText ?: 'empty')",
            unless = "#result.content.empty" // No cachear si la lista está vacía
    )
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
