package com.adri.kids.orders.infrastructure.adapter.in.dto.request;

import com.adri.kids.orders.app.usecases.additem.AddItemCommand;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AddItemRequest(@NotNull(message = "El id de la variante no puede ser nulo") UUID variantId,
                             @NotNull(message = "La cantidad no puede ser nula")
                             @Positive(message = "La cantidad debe ser un numero positivo") Integer quantity) {

    public AddItemCommand toCommand(UUID orderId, String customerUsername) {
        return new AddItemCommand(orderId, this.variantId, this.quantity, customerUsername);
    }
}
