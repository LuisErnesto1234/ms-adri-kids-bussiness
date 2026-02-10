package com.test.product.inventory.application.usecases.createsize;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.enums.InventoryStatus;
import com.test.product.inventory.domain.enums.TypeProduct;
import com.test.product.inventory.domain.model.Size;

public record CreateSizeCommand(String name, TypeProduct type,
                                String sortOrder, InventoryStatus status) implements Command<Size> {
}
