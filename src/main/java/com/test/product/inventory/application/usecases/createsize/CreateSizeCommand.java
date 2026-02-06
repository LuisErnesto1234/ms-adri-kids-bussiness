package com.test.product.inventory.application.usecases.createsize;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.enums.Status;
import com.test.product.inventory.domain.enums.TypeProduct;
import com.test.product.inventory.domain.model.Size;

public record CreateSizeCommand(String name, TypeProduct type,
                                String sortOrder, Status status) implements Command<Size> {
}
