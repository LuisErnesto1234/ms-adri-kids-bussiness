package com.test.product.inventory.domain.model.details;

import com.test.product.inventory.domain.enums.InventoryStatus;
import com.test.product.inventory.domain.model.Category;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ProductDetails(UUID id, Category category, String name,
                             String description, BigDecimal basePrice,
                             String imageUrl, List<ProductVariantDetails> productVariants,
                             Instant createdAt, Instant updatedAt, InventoryStatus status, Boolean isFeatured) {
}
