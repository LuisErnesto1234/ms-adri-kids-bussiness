package com.adri.kids.catalog.infrastructure.adapter.in.dto.request;

import com.adri.kids.catalog.application.size.command.CreateSizeCommand;
import com.adri.kids.shared.domain.enums.InventoryStatus;
import com.adri.kids.shared.domain.enums.TypeProduct;
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
