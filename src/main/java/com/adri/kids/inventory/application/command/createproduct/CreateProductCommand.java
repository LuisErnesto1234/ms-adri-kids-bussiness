package com.adri.kids.inventory.application.command.createproduct;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.domain.enums.InventoryStatus;
import com.adri.kids.inventory.domain.model.details.ProductDetails;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductCommand(UUID categoryId, String name,
                                   String description, BigDecimal basePrice, Boolean isFeatured,
                                   String imageUrl, InventoryStatus status) implements Command<ProductDetails> {
}
