package com.test.product.inventory.application.usecases.createproductvariant;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.enums.InventoryStatus;
import com.test.product.inventory.domain.model.details.ProductVariantDetails;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductVariantCommand(UUID productId, UUID colorId, UUID sizeId, String sku,
                                          Integer stockQuantity, BigDecimal priceAdjustment,
                                          String imageUrl, InventoryStatus status) implements Command<ProductVariantDetails> {
}
