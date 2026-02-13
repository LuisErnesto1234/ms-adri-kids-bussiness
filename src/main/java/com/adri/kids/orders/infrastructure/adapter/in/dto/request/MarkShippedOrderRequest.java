package com.adri.kids.orders.infrastructure.adapter.in.dto.request;

import com.adri.kids.orders.app.usecases.markshippedorder.MarkShippedOrderCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MarkShippedOrderRequest(
        @NotNull(message = "El carrier no deberia estar en nulo")
        @NotBlank(message = "El carrier no deberia estar en blanco")
        String carrier) {

    public MarkShippedOrderCommand toCommand(UUID orderId){
        if (orderId == null) throw new IllegalArgumentException("El order id no puede estar nulo");

        return new MarkShippedOrderCommand(orderId, this.carrier);
    }

}
