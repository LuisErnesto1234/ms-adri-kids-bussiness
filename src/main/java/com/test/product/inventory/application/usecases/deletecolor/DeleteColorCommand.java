package com.test.product.inventory.application.usecases.deletecolor;

import an.awesome.pipelinr.Command;

import java.util.UUID;

public record DeleteColorCommand(UUID id) implements Command<Void> {
}
