package com.test.product.inventory.domain.model.details;

import com.test.product.inventory.domain.enums.InventoryStatus;
import com.test.product.inventory.domain.model.Color;
import com.test.product.inventory.domain.model.Product;
import com.test.product.inventory.domain.model.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductVariantDetails(UUID id, Product product, Color color, Size size, String Sku, Integer stockQuantity,
                                    BigDecimal priceAdjustment, String imageUrl, Instant createdAt, Instant updatedAt,
                                    InventoryStatus status) {
}
