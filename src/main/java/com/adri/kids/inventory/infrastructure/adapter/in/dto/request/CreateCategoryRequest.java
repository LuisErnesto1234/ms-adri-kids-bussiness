package com.adri.kids.inventory.infrastructure.adapter.in.dto.request;

import com.adri.kids.inventory.application.command.createcategory.CreateCategoryCommand;
import com.adri.kids.shared.domain.enums.GeneralStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCategoryRequest(
        @NotBlank(message = "El nombre es requerido")
        @NotNull(message = "El nombre no puede ser nulo")
        String name,
        @NotNull(message = "La descripcion no puede ser un valor nulo")
        String description,
        String urlImage,
        GeneralStatus status) {

    public CreateCategoryCommand toCommand() {
        return new CreateCategoryCommand(this.name, this.description, this.urlImage, this.status);
    }
}
