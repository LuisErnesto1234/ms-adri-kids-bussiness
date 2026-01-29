package com.test.product.inventory.domain.port.in.color_use_case;

import com.test.product.inventory.domain.model.Color;
import com.test.product.inventory.domain.port.in.command.color.CreateColorCommand;

public interface CreateColorUseCase {

    Color createColor(CreateColorCommand command);
}
