package com.adri.kids.product.infrastructure.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.adri.kids.shared.domain.enums.InventoryStatus;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Jacksonized
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
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