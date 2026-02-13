package com.adri.kids.orders.app.usecases.markshippedorder;

import com.adri.kids.orders.domain.model.Order;
import com.adri.kids.orders.domain.model.exeception.OrderNotFoundException;
import com.adri.kids.orders.domain.port.out.OrderRepositoryPort;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MarkShippedOrderHandler implements Command.Handler<MarkShippedOrderCommand, Order> {

    private final OrderRepositoryPort orderRepositoryPort;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, timeout = 500)
    @Override
    public Order handle(MarkShippedOrderCommand command) {
        MarkShippedOrderCommand markShippedOrderCommand =
                MarkShippedOrderCommand.createCommand(command.orderId(), command.carrier());

        Order orderToShip = orderRepositoryPort.findById(markShippedOrderCommand.orderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found for shipping"));

        orderToShip.markAsShipped(markShippedOrderCommand.carrier());
        return orderRepositoryPort.save(orderToShip);
    }
}
