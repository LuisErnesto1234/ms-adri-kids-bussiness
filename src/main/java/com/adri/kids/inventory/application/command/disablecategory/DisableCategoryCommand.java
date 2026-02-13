package com.adri.kids.inventory.application.command.disablecategory;

import an.awesome.pipelinr.Command;

import java.util.UUID;

public record DisableCategoryCommand(UUID categoryId) implements Command<UUID> {
}
