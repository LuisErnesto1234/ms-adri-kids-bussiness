package com.adri.kids.catalog.infrastructure.adapter.in.dto.request;

import com.adri.kids.catalog.application.category.command.CreateCategoryCommand;

import jakarta.validation.constraints.*;

public record CreateCategoryRequest(
        @NotNull(message = "El nombre de la categoria no puede ser nulo.")
        @NotBlank(message = "El nombre de la categoria no puede estar en blanco.")
        @Size(max = 100, min = 2, message = "El nombre de la categoria debe tener entre 2 y 100 caracteres.")
        String name,
        @NotNull(message = "El slug de la categoria no puede ser nulo.")
        @NotBlank(message = "El slug de la categoria no puede estar en blanco.")
        String slug,
        @Size(min = 50, message = "La descripción corta no puede tener más de 50 caracteres.")
        String descriptionShort,
        @Size(min = 40, max = 500, message = "La descripción larga debe tener entre 40 y 500 caracteres.")
        String descriptionLong,
        @Size(max = 255, message = "La URL de la imagen no puede tener más de 255 caracteres.")
        @Pattern(regexp = "^(https?://).+$", message = "La URL de la imagen debe ser una URL válida que comience con 'http://' o https://")
        String urlImage,
        @Positive(message = "El número de orden debe ser un valor positivo.")
        int numberOfOrder,
        @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "El código de color debe ser un valor hexadecimal válido, por ejemplo, #FFFFFF o #FFF.")
        String colorCode,
        boolean isShowMainMenu) {

    public CreateCategoryCommand toCommand() {
        return new CreateCategoryCommand(
                this.name,
                this.slug,
                this.descriptionShort,
                this.descriptionLong,
                this.urlImage,
                this.numberOfOrder,
                this.colorCode,
                this.isShowMainMenu);
    }
}
