package com.test.product.inventory.application.usecases.incrementstockproductvariant;

import an.awesome.pipelinr.Command;

import java.util.UUID;

public record IncrementStockProductVariantCommand(UUID productVariantId, Integer quantity)
        implements Command<Void> {
}
