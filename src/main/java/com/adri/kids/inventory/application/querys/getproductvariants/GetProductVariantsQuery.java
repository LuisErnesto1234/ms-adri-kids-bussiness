package com.adri.kids.inventory.application.querys.getproductvariants;

import an.awesome.pipelinr.Command;

import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.productvariant.ProductVariantCardResponse;
import com.adri.kids.shared.domain.dtos.PagedResult;

import org.springframework.data.domain.Pageable;

public record GetProductVariantsQuery(Pageable pageable, String searchText)
        implements Command<PagedResult<ProductVariantCardResponse>> {
}
