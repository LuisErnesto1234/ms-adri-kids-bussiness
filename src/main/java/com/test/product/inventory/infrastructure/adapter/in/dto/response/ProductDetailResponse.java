package com.test.product.inventory.infrastructure.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.product.inventory.domain.enums.InventoryStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record ProductDetailResponse(
        UUID id,
        String name,
        String description,
        String categoryName,
        BigDecimal basePrice,
        String imageUrl,
        InventoryStatus status,
        List<ProductVariantCardResponse> variants
){}