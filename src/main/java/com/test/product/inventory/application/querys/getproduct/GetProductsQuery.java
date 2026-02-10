package com.test.product.inventory.application.querys.getproduct;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ProductCardResponse;
import com.test.product.shared.domain.dtos.PagedResult;

import org.springframework.data.domain.Pageable;

public record GetProductsQuery(Pageable pageable, String filterText)
        implements Command<PagedResult<ProductCardResponse>> {
}
