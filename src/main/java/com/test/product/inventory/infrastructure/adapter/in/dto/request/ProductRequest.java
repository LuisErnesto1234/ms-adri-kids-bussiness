package com.test.product.inventory.infrastructure.adapter.in.dto.request;

import com.test.product.inventory.domain.enums.Status;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRequest(UUID categoryId, String name, String description, BigDecimal basePrice,
                             String imageUrl, Status status) {
}
