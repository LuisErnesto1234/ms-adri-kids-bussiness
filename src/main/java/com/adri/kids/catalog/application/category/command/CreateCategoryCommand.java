package com.adri.kids.catalog.application.category.command;

import an.awesome.pipelinr.Command;
import com.adri.kids.catalog.domain.model.Category;
import com.adri.kids.shared.domain.dtos.UploadedImage;

public record CreateCategoryCommand(String name,
                                    String slug,
                                    String descriptionShort,
                                    String descriptionLong,
                                    UploadedImage image,
                                    int numberOfOrder,
                                    String colorCode,
                                    boolean isShowMainMenu)
        implements Command<Category> {

    public CreateCategoryCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede ser nulo o vacío");
        }
        if (slug == null || slug.isBlank()) {
            throw new IllegalArgumentException("El slug de la categoría no puede ser nulo o vacío");
        }
        if (image == null) {
            throw new IllegalArgumentException("La imagen no puede ser nula.");
        }
        if (numberOfOrder < 0 || numberOfOrder > 100) {
            throw new IllegalArgumentException("El número de orden debe ser un valor positivo y no mayor a 100.");
        }
        if (colorCode == null || colorCode.isBlank()) {
            throw new IllegalArgumentException("El color de la imagen no puede ser nulo.");
        }
        if (descriptionShort != null && (!descriptionShort.isBlank())) {
            throw new IllegalArgumentException("El description de la imagen no puede ser nulo.");
        }
        if (descriptionLong != null && (!descriptionLong.isBlank())) {
            throw new IllegalArgumentException("El description de la imagen no puede ser nulo.");
        }
    }

}
