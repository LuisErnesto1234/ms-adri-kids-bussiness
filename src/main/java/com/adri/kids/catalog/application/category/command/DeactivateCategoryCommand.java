package com.adri.kids.catalog.application.category.command;

import an.awesome.pipelinr.Command;
import com.adri.kids.catalog.domain.model.Category;

import java.util.UUID;

public record DeactivateCategoryCommand(UUID categoryId) implements Command<Category> {
    public DeactivateCategoryCommand {
        if (categoryId == null) {
            throw new IllegalArgumentException("El id de la categoria no puede ser nulo.");
        }
    }
}
