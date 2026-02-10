package com.test.product.inventory.application.querys.getproductvariants;

import an.awesome.pipelinr.Command;

import com.test.product.inventory.domain.port.out.ProductVariantRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ProductVariantCardResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.ProductVariantRestMapper;
import com.test.product.shared.domain.dtos.PagedResult;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetProductVariantsHandler implements
        Command.Handler<GetProductVariantsQuery, PagedResult<ProductVariantCardResponse>> {

    private final ProductVariantRepositoryPort productVariantRepositoryPort;
    private final ProductVariantRestMapper restMapper;

    @Transactional(readOnly = true)
    @Override
    @Cacheable(
            value = "product_variant_page",
            key = "#query.pageable().pageNumber + '-' + #query.pageable().pageSize + '-' + #query.searchText()",
            unless = "#result.content().empty"
    )
    public PagedResult<ProductVariantCardResponse> handle(GetProductVariantsQuery query) {
        var domainPage = productVariantRepositoryPort.findAll(query.pageable());

        ArrayList<ProductVariantCardResponse> mutableContent = domainPage.getContent()
                .stream().map(restMapper::toResponse)
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
