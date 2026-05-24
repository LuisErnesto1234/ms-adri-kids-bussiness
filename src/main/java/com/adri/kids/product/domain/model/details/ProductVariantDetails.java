package com.adri.kids.product.domain.model.details;

import com.adri.kids.shared.domain.enums.InventoryStatus;
import com.adri.kids.catalog.domain.model.Color;
import com.adri.kids.product.domain.model.Product;
import com.adri.kids.catalog.domain.model.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductVariantDetails(UUID id, Product product, Color color, Size size, String sku,
                                    Integer stockQuantity, BigDecimal priceAdjustment, String imageUrl,
                                    Instant createdAt, Instant updatedAt, InventoryStatus status) {
}
