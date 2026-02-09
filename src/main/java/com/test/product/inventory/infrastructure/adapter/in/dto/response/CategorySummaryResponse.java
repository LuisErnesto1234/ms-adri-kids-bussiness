package com.test.product.inventory.infrastructure.adapter.in.dto.response;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record CategorySummaryResponse(UUID id, String name,
                                      String description, String urlImage,
                                      Instant createdAt, Instant updatedAt) {
}
