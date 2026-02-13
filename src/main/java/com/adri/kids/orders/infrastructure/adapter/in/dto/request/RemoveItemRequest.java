package com.adri.kids.orders.infrastructure.adapter.in.dto.request;

import com.adri.kids.orders.app.usecases.removeitem.RemoveItemCommand;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record RemoveItemRequest(
        @NotNull(message = "El id de los items no puede ser nulo")
        @NotEmpty(message = "El id de los items no puede estar vacio")
        Set<UUID> idItems) {

    public RemoveItemCommand toCommand(UUID orderId, UUID customerId){
        if (orderId == null) throw new IllegalArgumentException("El id de la orden no puede ser nulo");
        if (customerId == null) throw new IllegalArgumentException("El id del cliente no puede ser nulo");

        return new RemoveItemCommand(orderId, this.idItems, customerId);
    }

}
