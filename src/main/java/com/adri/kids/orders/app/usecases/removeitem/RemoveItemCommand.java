package com.adri.kids.orders.app.usecases.removeitem;

import an.awesome.pipelinr.Command;
import com.adri.kids.orders.domain.model.Order;

import java.util.Set;
import java.util.UUID;

public record RemoveItemCommand(UUID orderId, Set<UUID> idItems, UUID customerId) implements Command<Order> {
}
