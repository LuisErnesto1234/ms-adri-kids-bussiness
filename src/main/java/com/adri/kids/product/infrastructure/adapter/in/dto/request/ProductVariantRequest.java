package com.adri.kids.product.infrastructure.adapter.in.dto.request;

import com.adri.kids.product.application.product.commandvariant.CreateProductVariantCommand;
import com.adri.kids.shared.domain.enums.InventoryStatus;

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
