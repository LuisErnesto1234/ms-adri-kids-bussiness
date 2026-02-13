package com.test.product.inventory.infrastructure.adapter.in.dto.response.color;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.test.product.inventory.domain.enums.InventoryStatus;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Jacksonized
@Builder
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public record ColorSummaryResponse(UUID id, String name, String hexCode,
                                   Instant createdAt, Instant updatedAt, InventoryStatus status) {
}
