package com.test.product.inventory.infrastructure.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.product.inventory.domain.enums.Status;
import com.test.product.inventory.domain.model.ProductVariant;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductSummaryResponse(UUID id, CategorySummaryResponse categoryResponse, String name,
                                     String description, BigDecimal basePrice, String imageUrl,
                                     List<ProductVariant> productVariants,
                                     Instant createdAt, Instant updatedAt, Status status) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
