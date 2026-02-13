package com.adri.kids.inventory.application.querys.getproductbyid;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.product.ProductDetailResponse;

import java.util.UUID;

public record GetProductByIdQuery(UUID id) implements Command<ProductDetailResponse> {
}
