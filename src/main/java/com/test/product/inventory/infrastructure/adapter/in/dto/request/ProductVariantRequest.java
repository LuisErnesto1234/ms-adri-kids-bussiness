package com.test.product.inventory.infrastructure.adapter.in.dto.request;

import com.test.product.inventory.application.usecases.createproductvariant.CreateProductVariantCommand;
import com.test.product.inventory.domain.enums.InventoryStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductVariantRequest(UUID productId, UUID colorId, UUID sizeId, String sku,
                                    Integer stockQuantity, BigDecimal priceAdjustment,
                                    String imageUrl, InventoryStatus status) {

    public CreateProductVariantCommand toCommand(){
        return new CreateProductVariantCommand(this.productId, this.colorId, this.sizeId, this.sku,
                this.stockQuantity, this.priceAdjustment, this.imageUrl, this.status);
    }

}
