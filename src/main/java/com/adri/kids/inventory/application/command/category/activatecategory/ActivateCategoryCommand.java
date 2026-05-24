package com.adri.kids.inventory.application.command.category.activatecategory;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.domain.model.Category;

import java.util.UUID;

public record ActivateCategoryCommand(UUID categoryId) implements Command<Category> {
    public ActivateCategoryCommand {
        if (categoryId == null) {
            throw new IllegalArgumentException("El id de la categoria no puede ser nulo.");
        }
    }
}
