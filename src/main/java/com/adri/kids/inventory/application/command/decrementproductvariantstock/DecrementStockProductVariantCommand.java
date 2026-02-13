package com.adri.kids.inventory.application.command.decrementproductvariantstock;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.domain.model.ProductVariant;

import java.util.UUID;

public record DecrementStockProductVariantCommand(UUID productVariantId, Integer stock)
        implements Command<ProductVariant> {
}
