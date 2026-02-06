package com.test.product.orders.app.usecases.applycoupon;

import an.awesome.pipelinr.Command;
import com.test.product.orders.domain.model.Order;

import java.util.UUID;

public record ApplyCouponCommand(
        UUID orderId,
        String couponCode
) implements Command<Order> {
}