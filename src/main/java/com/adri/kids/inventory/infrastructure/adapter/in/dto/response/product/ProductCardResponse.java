package com.adri.kids.inventory.infrastructure.adapter.in.dto.response.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.adri.kids.inventory.domain.enums.InventoryStatus;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Jacksonized
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public record ProductCardResponse(UUID id, String categoryName, String name,
                                  BigDecimal basePrice, String imageUrl,
                                  Integer variantsCount,
                                  Boolean isNew,
                                  InventoryStatus status) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
