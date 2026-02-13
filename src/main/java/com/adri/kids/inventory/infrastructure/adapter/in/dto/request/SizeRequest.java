package com.adri.kids.inventory.infrastructure.adapter.in.dto.request;

import com.adri.kids.inventory.application.command.createsize.CreateSizeCommand;
import com.adri.kids.inventory.domain.enums.InventoryStatus;
import com.adri.kids.inventory.domain.enums.TypeProduct;
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
