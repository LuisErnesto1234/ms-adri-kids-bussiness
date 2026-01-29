package com.test.product.inventory.infrastructure.adapter.in.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotBlank(message = "El nombre es requerido")
        @NotNull(message = "El nombre no puede ser nulo")
        String name,
        @NotNull(message = "La descripcion no puede ser un valor nulo")
        String description,
        String urlImage) {
}
