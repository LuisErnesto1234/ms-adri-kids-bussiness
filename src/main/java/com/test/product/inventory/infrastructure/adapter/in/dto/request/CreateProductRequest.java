package com.test.product.inventory.infrastructure.adapter.in.dto.request;

import com.test.product.inventory.application.usecases.createproduct.CreateProductCommand;
import com.test.product.inventory.domain.enums.Status;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductRequest(UUID categoryId, String name,
                                   String description, BigDecimal basePrice,
                                   String imageUrl, Status status) {

    public CreateProductCommand toCommand(){
        return new CreateProductCommand(this.categoryId, this.name, this.description,
                this.basePrice, this.imageUrl, this.status);
    }

}
