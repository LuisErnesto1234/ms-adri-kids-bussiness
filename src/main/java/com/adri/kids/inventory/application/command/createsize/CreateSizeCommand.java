package com.adri.kids.inventory.application.command.createsize;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.domain.enums.InventoryStatus;
import com.adri.kids.inventory.domain.enums.TypeProduct;
import com.adri.kids.inventory.domain.model.Size;

public record CreateSizeCommand(String name, TypeProduct type,
                                String sortOrder, InventoryStatus status) implements Command<Size> {
}
