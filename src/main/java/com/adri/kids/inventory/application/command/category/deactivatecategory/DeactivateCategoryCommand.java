package com.adri.kids.inventory.application.command.category.deactivatecategory;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.domain.model.Category;

import java.util.UUID;

public record DeactivateCategoryCommand(UUID categoryId) implements Command<Category> {
    public DeactivateCategoryCommand {
        if (categoryId == null) {
            throw new IllegalArgumentException("El id de la categoria no puede ser nulo.");
        }
    }
}
