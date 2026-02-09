package com.test.product.inventory.application.querys.getproduct;

import an.awesome.pipelinr.Command;

import com.test.product.inventory.domain.port.out.ProductRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ProductSummaryResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.ProductRestMapper;
import com.test.product.shared.domain.PageMapper;
import com.test.product.shared.domain.PagedResult;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetProductsHandler implements Command.Handler<GetProductsQuery, PagedResult<ProductSummaryResponse>> {

    private final ProductRepositoryPort productRepositoryPort;
    private final ProductRestMapper productRestMapper;

    @Transactional(readOnly = true)
    @Override
    @Cacheable(
            value = "products_page", // Nombre de la "tabla" en Redis
            key = "#query.pageable().pageNumber + '-' + #query.pageable().pageSize + '-' + #query.filterText()",
            unless = "#result.content().empty" // No cachear si la lista está vacía
    )
    public PagedResult<ProductSummaryResponse> handle(GetProductsQuery query) {

        var domainPage = productRepositoryPort.findAllWithCategory(query.pageable());

        var dtoPage = domainPage.map(productRestMapper::toResponse);

        return PageMapper.fromPage(dtoPage);
    }
}
