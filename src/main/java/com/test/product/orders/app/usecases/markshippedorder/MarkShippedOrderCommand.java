package com.test.product.orders.app.usecases.markshippedorder;

import an.awesome.pipelinr.Command;
import com.test.product.orders.domain.model.Order;

import java.util.UUID;

public record MarkShippedOrderCommand(UUID orderId, String carrier) implements Command<Order> {

    public MarkShippedOrderCommand {
        if (this.orderId() == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }

        if (this.carrier() == null || this.carrier().isEmpty() || this.carrier().isBlank()) {
            throw new IllegalArgumentException("Carrier cannot be null or empty");
        }
    }

    public static MarkShippedOrderCommand createCommand(UUID orderId, String carrier) {
        return new MarkShippedOrderCommand(orderId, carrier);
    }
}
