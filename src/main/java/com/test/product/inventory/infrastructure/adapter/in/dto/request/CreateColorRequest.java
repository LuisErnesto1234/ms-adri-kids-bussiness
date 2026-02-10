package com.test.product.inventory.infrastructure.adapter.in.dto.request;

import com.test.product.inventory.application.usecases.createcolor.CreateColorCommand;
import com.test.product.inventory.domain.enums.InventoryStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateColorRequest(
        @NotNull(message = "El nombre del color no deberia esta como valor nulo")
        @NotBlank(message = "El nombre del color no deberia estar en blanco")
        String name,
        @NotNull(message = "El codigo hexadecimal del color no deberia esta como valor nulo")
        @NotBlank(message = "El codigo hexadecimal del color no deberia estar en blanco")
        String hexCode,
        @NotNull(message = "El status deberia ser un valor no nulo")
        InventoryStatus status) {

    public CreateColorCommand toCommand(){
        return new CreateColorCommand(this.name, this.hexCode, this.status);
    }
}
