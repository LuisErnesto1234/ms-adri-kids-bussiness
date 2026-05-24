package com.adri.kids.product.application.product.query;

import an.awesome.pipelinr.Command;
import com.adri.kids.product.infrastructure.adapter.in.dto.response.ProductDetailResponse;

import java.util.UUID;

public record GetProductByIdQuery(UUID id) implements Command<ProductDetailResponse> {
}
