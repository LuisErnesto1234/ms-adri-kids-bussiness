package com.test.product.inventory.domain.port.in.command.product;

import com.test.product.inventory.domain.enums.Status;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductCommand(UUID categoryId, String name, String description, BigDecimal basePrice,
                                   String imageUrl, Status status) {
}
