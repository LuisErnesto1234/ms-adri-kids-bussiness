package com.adri.kids.orders.app.usecases.setshippinginfo;

import an.awesome.pipelinr.Command;
import com.adri.kids.orders.domain.model.Address;
import com.adri.kids.orders.domain.model.Order;

import java.util.UUID;

public record SetShippingInfoCommand (
        UUID orderId,
        Address address
) implements Command<Order> {
}