package com.test.product.orders.app.usecases.setshippinginfo;

import com.test.product.orders.domain.model.Order;
import com.test.product.orders.domain.model.exeception.OrderNotFoundException;
import com.test.product.orders.domain.port.out.OrderRepositoryPort;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SetShippingInfoHandler implements Command.Handler<SetShippingInfoCommand, Order> {

    private final OrderRepositoryPort orderRepositoryPort;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, timeout = 500)
    @Override
    public Order handle(SetShippingInfoCommand command) {
        Order order = orderRepositoryPort.findById(command.orderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found for shipping address"));

        order.updateShippingAddress(command.address());

        return orderRepositoryPort.save(order);
    }
}
