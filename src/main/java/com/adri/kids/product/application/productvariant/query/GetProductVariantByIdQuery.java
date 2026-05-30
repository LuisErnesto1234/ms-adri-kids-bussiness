package com.adri.kids.product.application.productvariant.query;

import an.awesome.pipelinr.Command;
import com.adri.kids.product.infrastructure.adapter.in.dto.response.ProductVariantDetailResponse;

import java.util.UUID;

public record GetProductVariantByIdQuery(UUID id)
        implements Command<ProductVariantDetailResponse> {
}
