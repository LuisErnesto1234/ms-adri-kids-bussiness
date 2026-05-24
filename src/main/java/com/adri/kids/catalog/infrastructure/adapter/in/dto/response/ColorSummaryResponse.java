package com.adri.kids.catalog.infrastructure.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.adri.kids.shared.domain.enums.InventoryStatus;
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
