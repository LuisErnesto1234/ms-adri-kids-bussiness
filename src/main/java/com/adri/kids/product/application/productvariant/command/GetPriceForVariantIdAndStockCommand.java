package com.adri.kids.product.application.productvariant.command;

import an.awesome.pipelinr.Command;

import java.math.BigDecimal;
import java.util.UUID;

public record GetPriceForVariantIdAndStockCommand(UUID variantId, int quantity)
        implements Command<BigDecimal> {
}
