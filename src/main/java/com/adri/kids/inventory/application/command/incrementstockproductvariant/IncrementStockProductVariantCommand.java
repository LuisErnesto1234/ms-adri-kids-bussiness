package com.adri.kids.inventory.application.command.incrementstockproductvariant;

import an.awesome.pipelinr.Command;

import java.util.UUID;

public record IncrementStockProductVariantCommand(UUID productVariantId, Integer quantity)
        implements Command<Void> {
}
