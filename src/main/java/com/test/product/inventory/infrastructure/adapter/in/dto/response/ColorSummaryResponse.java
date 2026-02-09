package com.test.product.inventory.infrastructure.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.test.product.inventory.domain.enums.Status;

import java.time.Instant;
import java.util.UUID;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ColorSummaryResponse(UUID id, String name, String hexCode,
                                   Instant createdAt, Instant updatedAt, Status status) {
}
