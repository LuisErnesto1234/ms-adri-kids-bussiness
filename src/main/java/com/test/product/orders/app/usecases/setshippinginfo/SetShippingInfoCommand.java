package com.test.product.orders.app.usecases.setshippinginfo;

import an.awesome.pipelinr.Command;
import com.test.product.orders.domain.model.Address;
import com.test.product.orders.domain.model.Order;

import java.util.UUID;

public record SetShippingInfoCommand (
        UUID orderId,
        Address address
) implements Command<Order> {
}