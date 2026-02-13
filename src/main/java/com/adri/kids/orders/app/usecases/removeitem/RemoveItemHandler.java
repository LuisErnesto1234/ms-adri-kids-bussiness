package com.adri.kids.orders.app.usecases.removeitem;

import com.adri.kids.orders.domain.model.Order;
import com.adri.kids.orders.domain.model.OrderItem;
import com.adri.kids.orders.domain.model.exeception.OrderItemNotFoundException;
import com.adri.kids.orders.domain.model.exeception.OrderNotFoundException;
import com.adri.kids.orders.domain.port.out.OrderItemRepositoryPort;
import com.adri.kids.orders.domain.port.out.OrderRepositoryPort;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RemoveItemHandler implements Command.Handler<RemoveItemCommand, Order> {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderItemRepositoryPort orderItemRepositoryPort;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, timeout = 500)
    @Override
    public Order handle(RemoveItemCommand command) {

        if (command.idItems().isEmpty()) {
            throw new IllegalArgumentException("Item list cannot be empty");
        }

        // 2. Obtener orden
        Order order = orderRepositoryPort.findByCustomerId(command.customerId())
                .orElseThrow(() -> new OrderNotFoundException(command.customerId().toString()));

        // 3. Obtener items
        Set<OrderItem> items = orderItemRepositoryPort.findAllById(command.idItems());

        if (items.isEmpty()) {
            throw new OrderItemNotFoundException(command.idItems().toString());
        }

        // 4. Remover items
        Order updatedOrder = order.removeItem(items);

        return orderRepositoryPort.save(updatedOrder);
    }
}
