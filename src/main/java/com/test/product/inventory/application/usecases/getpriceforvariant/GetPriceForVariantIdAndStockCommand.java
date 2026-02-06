package com.test.product.inventory.application.usecases.getpriceforvariant;

import an.awesome.pipelinr.Command;

import java.math.BigDecimal;
import java.util.UUID;

public record GetPriceForVariantIdAndStockCommand(UUID variantId, int quantity)
        implements Command<BigDecimal> {
}
