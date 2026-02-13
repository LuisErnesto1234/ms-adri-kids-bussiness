package com.adri.kids.inventory.application.command.updateproduct;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.domain.enums.InventoryStatus;

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