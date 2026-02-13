package com.adri.kids.orders.app.usecases.applycoupon;

import an.awesome.pipelinr.Command;
import com.adri.kids.orders.domain.model.Order;

import java.util.UUID;

public record ApplyCouponCommand(
        UUID orderId,
        String couponCode
) implements Command<Order> {
}