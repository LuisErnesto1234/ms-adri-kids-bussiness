package com.adri.kids.product.application.product.command;

import an.awesome.pipelinr.Command;
import com.adri.kids.shared.domain.enums.InventoryStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateProductCommand(
        UUID productId,
        UUID categoryId,
        String name,
        String description,
        BigDecimal basePrice,
        String imageUrl,
        Boolean isFeatured,
        InventoryStatus status
) implements Command<UUID> {
}