package com.adri.kids.product.application.productvariant.command;

import an.awesome.pipelinr.Command;

import java.util.UUID;

public record IncrementStockProductVariantCommand(UUID productVariantId, Integer quantity)
        implements Command<Void> {
}
