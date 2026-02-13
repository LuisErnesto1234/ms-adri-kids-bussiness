package com.test.product.inventory.application.usecases.disablecategory;

import an.awesome.pipelinr.Command;

import java.util.UUID;

public record DisableCategoryCommand(UUID categoryId) implements Command<UUID> {
}
