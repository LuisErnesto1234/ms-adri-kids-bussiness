package com.adri.kids.catalog.application.size.command;

import an.awesome.pipelinr.Command;
import com.adri.kids.shared.domain.enums.InventoryStatus;
import com.adri.kids.shared.domain.enums.TypeProduct;
import com.adri.kids.catalog.domain.model.Size;

public record CreateSizeCommand(String name, TypeProduct type,
                                String sortOrder, InventoryStatus status) implements Command<Size> {
}
