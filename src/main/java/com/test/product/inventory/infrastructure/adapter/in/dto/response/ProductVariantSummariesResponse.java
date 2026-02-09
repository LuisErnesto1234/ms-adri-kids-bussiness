package com.test.product.inventory.infrastructure.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.product.inventory.domain.enums.Status;
import com.test.product.inventory.domain.model.Color;
import com.test.product.inventory.domain.model.Product;
import com.test.product.inventory.domain.model.Size;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record ProductVariantSummariesResponse(UUID id, Product product, Color color, Size size, String Sku, Integer stockQuantity,
                                              BigDecimal priceAdjustment, String imageUrl, Instant createdAt, Instant updatedAt,
                                              Status status) {
}
