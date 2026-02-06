package com.test.product.orders.app.usecases.cancelorder;

import an.awesome.pipelinr.Command;
import com.test.product.orders.domain.model.Order;

import java.util.UUID;

public record CancelOrderCommand(UUID orderId) implements Command<Order> {
}
