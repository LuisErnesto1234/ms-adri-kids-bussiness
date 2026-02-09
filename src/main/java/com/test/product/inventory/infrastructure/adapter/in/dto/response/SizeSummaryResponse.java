package com.test.product.inventory.infrastructure.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.test.product.inventory.domain.enums.Status;
import com.test.product.inventory.domain.enums.TypeProduct;

import java.time.Instant;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SizeSummaryResponse(UUID id, String name, TypeProduct type, String sortOrder,
                                  Instant createdAt, Instant updatedAt,
                                  Status status) {
}
