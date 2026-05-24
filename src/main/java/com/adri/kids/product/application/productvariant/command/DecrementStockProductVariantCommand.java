package com.adri.kids.product.application.productvariant.command;

import an.awesome.pipelinr.Command;
import com.adri.kids.product.domain.model.ProductVariant;

import java.util.UUID;

public record DecrementStockProductVariantCommand(UUID productVariantId, Integer stock)
        implements Command<ProductVariant> {
}
