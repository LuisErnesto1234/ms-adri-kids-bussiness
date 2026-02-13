package com.adri.kids.orders.app.usecases.additem;

import an.awesome.pipelinr.Command;
import com.adri.kids.orders.domain.model.Order;

import java.util.UUID;

public record AddItemCommand(UUID orderId,
                             UUID variantId,
                             Integer quantity,
                             String customerUsername) implements Command<Order> {
}
