package com.test.product.inventory.infrastructure.adapter.in.dto.request;

import com.test.product.inventory.domain.enums.Status;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductVariantRequest(UUID productId, UUID colorId, UUID sizeId, String sku,
                                    Integer stockQuantity, BigDecimal priceAdjustment,
                                    String imageUrl, Status status) {
}
