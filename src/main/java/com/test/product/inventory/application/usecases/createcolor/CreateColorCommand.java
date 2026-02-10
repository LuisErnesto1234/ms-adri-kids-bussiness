package com.test.product.inventory.application.usecases.createcolor;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.enums.InventoryStatus;
import com.test.product.inventory.domain.model.Color;

public record CreateColorCommand(String name,
                                 String hexCode,
                                 InventoryStatus status) implements Command<Color> {
}
