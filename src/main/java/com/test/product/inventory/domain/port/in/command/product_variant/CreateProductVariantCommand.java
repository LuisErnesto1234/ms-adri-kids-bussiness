package com.test.product.inventory.domain.port.in.command.product_variant;

import com.test.product.inventory.domain.enums.Status;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductVariantCommand(UUID productId, UUID colorId, UUID sizeId, String sku,
                                          Integer stockQuantity, BigDecimal priceAdjustment,
                                          String imageUrl, Status status) {
}
