package com.test.product.inventory.infrastructure.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.product.inventory.domain.enums.InventoryStatus;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductCardResponse(UUID id, String categoryName, String name,
                                  BigDecimal basePrice, String imageUrl,
                                  Integer variantsCount,
                                  Boolean isNew,
                                  InventoryStatus status) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
