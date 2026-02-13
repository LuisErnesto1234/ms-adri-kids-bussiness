package com.test.product.inventory.infrastructure.adapter.in.dto.response.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Jacksonized
@Builder
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public record CategoryCardResponse(UUID id, String name,
                                      String description, String urlImage,
                                      Instant createdAt, Instant updatedAt) {
}
