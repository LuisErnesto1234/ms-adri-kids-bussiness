package com.adri.kids.inventory.infrastructure.adapter.in.dto.response.productvariant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.adri.kids.inventory.domain.enums.InventoryStatus;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Jacksonized
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public record ProductVariantDetailResponse(UUID id, String sku, Integer stock, BigDecimal price,
                                           String colorName, String colorHexCode, String sizeName,
                                           String sizeSortOrder, String imageUrl, InventoryStatus status,
                                           String name) {
}
