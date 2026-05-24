package com.adri.kids.product.application.product.query;

import an.awesome.pipelinr.Command;
import com.adri.kids.product.infrastructure.adapter.in.dto.response.ProductCardResponse;
import com.adri.kids.shared.domain.dtos.PagedResult;

import org.springframework.data.domain.Pageable;

public record GetProductsQuery(Pageable pageable, String filterText)
        implements Command<PagedResult<ProductCardResponse>> {
}
