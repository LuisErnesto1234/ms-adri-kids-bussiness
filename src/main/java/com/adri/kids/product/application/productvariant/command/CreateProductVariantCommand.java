package com.adri.kids.product.application.productvariant.command;

import an.awesome.pipelinr.Command;
import com.adri.kids.shared.domain.enums.InventoryStatus;
import com.adri.kids.product.domain.model.details.ProductVariantDetails;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductVariantCommand(UUID productId, UUID colorId, UUID sizeId, String sku,
                                          Integer stockQuantity, BigDecimal priceAdjustment,
                                          String imageUrl, InventoryStatus status) implements Command<ProductVariantDetails> {
}
