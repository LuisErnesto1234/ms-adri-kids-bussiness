package com.adri.kids.orders.app.usecases.cancelorder;

import an.awesome.pipelinr.Command;
import com.adri.kids.orders.domain.model.Order;

import java.util.UUID;

public record CancelOrderCommand(UUID orderId) implements Command<Order> {
}
