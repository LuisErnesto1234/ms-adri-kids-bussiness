package com.adri.kids.product.application.productvariant.query;

import an.awesome.pipelinr.Command;

import com.adri.kids.product.infrastructure.adapter.in.dto.response.ProductVariantCardResponse;
import com.adri.kids.shared.domain.dtos.PagedResult;

import org.springframework.data.domain.Pageable;

public record GetProductVariantsQuery(Pageable pageable, String searchText)
        implements Command<PagedResult<ProductVariantCardResponse>> {
}
