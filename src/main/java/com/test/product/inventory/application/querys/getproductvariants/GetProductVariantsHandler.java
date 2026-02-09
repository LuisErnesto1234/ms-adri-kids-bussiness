package com.test.product.inventory.application.querys.getproductvariants;

import an.awesome.pipelinr.Command;

import com.test.product.inventory.domain.port.out.ProductVariantRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ProductVariantSummariesResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.ProductVariantRestMapper;
import com.test.product.shared.domain.PageMapper;
import com.test.product.shared.domain.PagedResult;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetProductVariantsHandler implements
        Command.Handler<GetProductVariantsQuery, PagedResult<ProductVariantSummariesResponse>> {

    private final ProductVariantRepositoryPort productVariantRepositoryPort;
    private final ProductVariantRestMapper restMapper;

    @Transactional(readOnly = true)
    @Override
    @Cacheable(
            value = "product_variant_page",
            key = "#query.pageable().pageNumber + '-' + #query.pageable().pageSize + '-' + #query.searchText()",
            unless = "#result.content().empty"
    )
    public PagedResult<ProductVariantSummariesResponse> handle(GetProductVariantsQuery query) {
        var domainPage = productVariantRepositoryPort.findAll(query.pageable());

        var dtoPage = domainPage.map(restMapper::toResponse);

        return PageMapper.fromPage(dtoPage);
    }
}
