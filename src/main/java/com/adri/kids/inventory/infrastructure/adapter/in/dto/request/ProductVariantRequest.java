package com.adri.kids.inventory.infrastructure.adapter.in.dto.request;

import com.adri.kids.inventory.application.command.createproductvariant.CreateProductVariantCommand;
import com.adri.kids.inventory.domain.enums.InventoryStatus;

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
