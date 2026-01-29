package com.test.product.inventory.domain.port.in.command.product_variant;

import java.util.UUID;

public record IncrementStockProductVariantCommand(UUID productVariantId, Integer quantity) {
}
