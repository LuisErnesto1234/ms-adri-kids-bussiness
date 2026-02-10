package com.test.product.inventory.infrastructure.adapter.in.dto.request;

import com.test.product.inventory.application.usecases.createsize.CreateSizeCommand;
import com.test.product.inventory.domain.enums.InventoryStatus;
import com.test.product.inventory.domain.enums.TypeProduct;
import lombok.Builder;

@Builder
public record SizeRequest(String name, TypeProduct type,
                          String sortOrder, InventoryStatus status) {

    public CreateSizeCommand toCommand(){
        return new CreateSizeCommand(
                this.name,
                this.type,
                this.sortOrder,
                this.status);
    }

}
