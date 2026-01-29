package com.test.product.inventory.domain.port.in.size;

import com.test.product.inventory.domain.model.Size;
import com.test.product.inventory.domain.port.in.command.size.CreateSizeCommand;

public interface CreateSizeUseCase {
    Size createSize(CreateSizeCommand command);
}
