package com.test.product.inventory.application.usecases.createproduct;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.enums.InventoryStatus;
import com.test.product.inventory.domain.model.details.ProductDetails;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductCommand(UUID categoryId, String name,
                                   String description, BigDecimal basePrice, Boolean isFeatured,
                                   String imageUrl, InventoryStatus status) implements Command<ProductDetails> {
}
