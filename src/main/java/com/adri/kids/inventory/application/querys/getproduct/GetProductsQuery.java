package com.adri.kids.inventory.application.querys.getproduct;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.product.ProductCardResponse;
import com.adri.kids.shared.domain.dtos.PagedResult;

import org.springframework.data.domain.Pageable;

public record GetProductsQuery(Pageable pageable, String filterText)
        implements Command<PagedResult<ProductCardResponse>> {
}
