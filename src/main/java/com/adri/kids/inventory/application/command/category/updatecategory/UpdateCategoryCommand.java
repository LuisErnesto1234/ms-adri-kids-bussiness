package com.adri.kids.inventory.application.command.category.updatecategory;

import an.awesome.pipelinr.Command;

import com.adri.kids.inventory.domain.model.Category;
import com.adri.kids.shared.domain.enums.GeneralStatus;

import java.util.UUID;

public record UpdateCategoryCommand(
        UUID categoryId,
        String name,
        String descriptionShort,
        String descriptionLong,
        String urlImage,
        int numberOfOrder,
        String colorCode,
        boolean isShowMainMenu,
        GeneralStatus status) implements Command<Category> {

    public UpdateCategoryCommand {
        if (categoryId == null) {
            throw new IllegalStateException("El id de la categoría no puede ser nulo.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalStateException("El nombre de la categoría no puede ser nulo o estar vacío.");
        }
    }
}
