package com.test.product.inventory.application.querys.getproductvariantsbyid;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.productvariant.ProductVariantDetailResponse;

import java.util.UUID;

public record GetProductVariantByIdQuery(UUID id)
        implements Command<ProductVariantDetailResponse> {
}
