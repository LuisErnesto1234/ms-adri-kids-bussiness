package com.adri.kids.product.infrastructure.adapter.in.dto.request;

import com.adri.kids.product.application.product.command.CreateProductCommand;
import com.adri.kids.shared.domain.enums.InventoryStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductRequest(UUID categoryId, String name,
                                   String description, BigDecimal basePrice,
                                   String imageUrl, Boolean isFeatured, InventoryStatus status) {

    public CreateProductCommand toCommand(){
        return new CreateProductCommand(this.categoryId, this.name, this.description,
                this.basePrice, this.isFeatured, this.imageUrl, this.status);
    }

}
