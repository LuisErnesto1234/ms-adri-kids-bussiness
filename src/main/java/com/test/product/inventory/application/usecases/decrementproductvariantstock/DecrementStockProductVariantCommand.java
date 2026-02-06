package com.test.product.inventory.application.usecases.decrementproductvariantstock;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.model.ProductVariant;

import java.util.UUID;

public record DecrementStockProductVariantCommand(UUID productVariantId, Integer stock)
        implements Command<ProductVariant> {
}
