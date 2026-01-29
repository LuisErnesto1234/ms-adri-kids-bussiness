package com.test.product.inventory.domain.port.in.command.product_variant;

import java.util.UUID;

public record DecrementStockProductVariantCommand(UUID productVariantId, Integer stock) {
}
