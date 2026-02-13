package com.test.product.inventory.infrastructure.adapter.in.dto.response.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.test.product.inventory.infrastructure.adapter.in.dto.response.product.ProductCardResponse;
import com.test.product.shared.domain.enums.GeneralStatus;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Jacksonized
@Builder
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public record CategoryDetailResponse(UUID id, String name,
                                     String description, String urlImage,
                                     List<ProductCardResponse>  productCards,
                                     Instant createdAt, Instant updatedAt, GeneralStatus status) {
}
