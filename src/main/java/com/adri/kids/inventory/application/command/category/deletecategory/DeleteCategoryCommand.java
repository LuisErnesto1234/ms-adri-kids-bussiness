package com.adri.kids.inventory.application.command.category.deletecategory;

import an.awesome.pipelinr.Command;

import java.util.UUID;

public record DeleteCategoryCommand(UUID categoryId) implements Command<Void> {
    public DeleteCategoryCommand {
        if (categoryId == null) {
            throw new IllegalArgumentException("El id de la categoria no puede ser nulo");
        }
    }
}
