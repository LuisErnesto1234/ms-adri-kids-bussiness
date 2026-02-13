package com.adri.kids.orders.infrastructure.adapter.in.dto.request;

import com.adri.kids.orders.app.usecases.setshippinginfo.SetShippingInfoCommand;
import com.adri.kids.orders.domain.model.Address;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record SetShippingInfoRequest(
        @NotNull(message = "El address no deberia estar en nulo")
        Address address) {

    public SetShippingInfoCommand toCommand(UUID orderId) {

        if (orderId == null) throw new IllegalArgumentException("El id de la orden no puede ser nulo");

        return new SetShippingInfoCommand(orderId, address);
    }

}
