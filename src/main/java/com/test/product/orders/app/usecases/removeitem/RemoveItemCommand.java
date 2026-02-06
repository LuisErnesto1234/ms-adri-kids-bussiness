package com.test.product.orders.app.usecases.removeitem;

import an.awesome.pipelinr.Command;
import com.test.product.orders.domain.model.Order;

import java.util.Set;
import java.util.UUID;

public record RemoveItemCommand(UUID orderId, Set<UUID> idItems, UUID customerId) implements Command<Order> {
}
