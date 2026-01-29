package com.test.product.inventory.domain.port.in.color_use_case;

import com.test.product.inventory.domain.port.in.command.color.DeleteColorCommand;

public interface DeleteColorUseCase {
    void deleteColor(DeleteColorCommand command);
}
