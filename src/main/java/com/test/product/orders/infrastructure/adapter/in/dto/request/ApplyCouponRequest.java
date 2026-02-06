package com.test.product.orders.infrastructure.adapter.in.dto.request;

import com.test.product.orders.app.usecases.applycoupon.ApplyCouponCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ApplyCouponRequest (
        @NotNull(message = "El cupon no deberia estar en blanco")
        @NotBlank(message = "El cupon no deberia estar en blanco")
        String couponCode) {

    public ApplyCouponCommand toCommand(final UUID orderId) {
        if (orderId == null) throw new IllegalArgumentException("El id de la orden no puede ser nulo");

        return new ApplyCouponCommand(orderId, this.couponCode);
    }
}
