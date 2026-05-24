package com.adri.kids.inventory.application.command.productvariant.getpriceforvariant;

import an.awesome.pipelinr.Command;

import java.math.BigDecimal;
import java.util.UUID;

public record GetPriceForVariantIdAndStockCommand(UUID variantId, int quantity)
        implements Command<BigDecimal> {
}
