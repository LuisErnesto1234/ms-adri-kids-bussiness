package com.test.product.inventory.application.usecases.createcolor;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.enums.Status;
import com.test.product.inventory.domain.model.Color;

public record CreateColorCommand(String name,
                                 String hexCode,
                                 Status status) implements Command<Color> {
}
