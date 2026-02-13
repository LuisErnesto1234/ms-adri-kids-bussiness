package com.adri.kids.orders.app.usecases.cancelorder;

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
public class CancelOrderHandler implements Command.Handler<CancelOrderCommand, Order> {

    private final OrderRepositoryPort orderRepositoryPort;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, timeout = 500)
    @Override
    public Order handle(CancelOrderCommand command) {
        Order orderToCancel =
                orderRepositoryPort.findById(command.orderId()).orElseThrow(() ->
                        new OrderNotFoundException("Order not found for cancellation"));

        orderToCancel.cancel();

        return orderRepositoryPort.save(orderToCancel);
    }
}
