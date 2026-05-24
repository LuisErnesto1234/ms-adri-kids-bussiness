package com.adri.kids.catalog.application.category.command;

import an.awesome.pipelinr.Command;
import com.adri.kids.catalog.domain.model.Category;

public record CreateCategoryCommand(String name,
                                    String slug,
                                    String descriptionShort,
                                    String descriptionLong,
                                    String urlImage,
                                    int numberOfOrder,
                                    String colorCode,
                                    boolean isShowMainMenu)
        implements Command<Category> {

    public CreateCategoryCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede ser nulo o vacío");
        }
    }

}
