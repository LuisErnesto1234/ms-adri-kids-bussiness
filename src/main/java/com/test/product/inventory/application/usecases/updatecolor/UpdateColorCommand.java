package com.test.product.inventory.application.usecases.updatecolor;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.enums.Status;
import com.test.product.inventory.domain.model.Color;

import java.util.UUID;

public record UpdateColorCommand(UUID id, String name,
                                 String hexCode, Status status) implements Command<Color> {
}
