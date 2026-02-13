package com.adri.kids.inventory.infrastructure.adapter.in.dto.response.size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.adri.kids.inventory.domain.enums.InventoryStatus;
import com.adri.kids.inventory.domain.enums.TypeProduct;

import java.time.Instant;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SizeSummaryResponse(UUID id, String name, TypeProduct type, String sortOrder,
                                  Instant createdAt, Instant updatedAt,
                                  InventoryStatus status) {
}
