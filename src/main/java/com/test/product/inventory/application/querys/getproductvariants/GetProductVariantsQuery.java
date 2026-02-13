package com.test.product.inventory.application.querys.getproductvariants;

import an.awesome.pipelinr.Command;

import com.test.product.inventory.infrastructure.adapter.in.dto.response.productvariant.ProductVariantCardResponse;
import com.test.product.shared.domain.dtos.PagedResult;

import org.springframework.data.domain.Pageable;

public record GetProductVariantsQuery(Pageable pageable, String searchText)
        implements Command<PagedResult<ProductVariantCardResponse>> {
}
