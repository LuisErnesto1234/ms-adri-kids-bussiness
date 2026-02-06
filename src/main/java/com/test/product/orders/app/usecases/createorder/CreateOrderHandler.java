package com.test.product.orders.app.usecases.createorder;

import an.awesome.pipelinr.Command;
import com.test.product.orders.domain.model.Order;
import com.test.product.orders.domain.port.out.OrderRepositoryPort;
import com.test.product.orders.domain.port.out.gateway.CustomerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateOrderHandler implements Command.Handler<CreateOrderCommand, UUID> {

    private final OrderRepositoryPort orderRepositoryPort;
    private final CustomerGateway customerGateway;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, timeout = 500)
    @Override
    public UUID handle(CreateOrderCommand command) {

        UUID customerId = customerGateway.getCustomerIdByUsername(command.username());

        // 1. Tu lógica de negocio
        var newOrder = Order.createOrder(customerId);

        // 2. Persistencia
        orderRepositoryPort.save(newOrder);

        // 3. Retorno
        return newOrder.id();
    }
}
