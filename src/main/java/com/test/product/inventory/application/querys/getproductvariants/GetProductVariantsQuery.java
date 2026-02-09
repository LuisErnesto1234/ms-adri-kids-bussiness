package com.test.product.inventory.application.querys.getproductvariants;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ProductVariantSummariesResponse;
import com.test.product.shared.domain.PagedResult;
import org.springframework.data.domain.Pageable;

public record GetProductVariantsQuery(Pageable pageable, String searchText)
        implements Command<PagedResult<ProductVariantSummariesResponse>> {
}
