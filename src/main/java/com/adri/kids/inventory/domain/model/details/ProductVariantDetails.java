package com.adri.kids.inventory.domain.model.details;

import com.adri.kids.inventory.domain.enums.InventoryStatus;
import com.adri.kids.inventory.domain.model.Color;
import com.adri.kids.inventory.domain.model.Product;
import com.adri.kids.inventory.domain.model.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductVariantDetails(UUID id, Product product, Color color, Size size, String sku,
                                    Integer stockQuantity, BigDecimal priceAdjustment, String imageUrl,
                                    Instant createdAt, Instant updatedAt, InventoryStatus status) {
}
