package com.test.product.orders.app.usecases.additem;

import com.test.product.orders.domain.model.Order;
import com.test.product.orders.domain.port.out.OrderRepositoryPort;
import com.test.product.orders.domain.port.out.gateway.CustomerGateway;
import com.test.product.orders.domain.port.out.gateway.InventoryGateway;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import an.awesome.pipelinr.Command;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddItemHandler implements Command.Handler<AddItemCommand, Order> {

    private final OrderRepositoryPort orderRepositoryPort;
    private final InventoryGateway inventoryGateway;
    private final CustomerGateway customerGateway;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, timeout = 500)
    @Override
    public Order handle(AddItemCommand command) {

        // Obtenemos el usuario
        UUID customerId = customerGateway.getCustomerIdByUsername(command.customerUsername());

        // 1. Obtener precio y validar stock
        BigDecimal unitPrice = inventoryGateway.getPriceForVariantAndHaveStock(
                command.variantId(),
                command.quantity()
        );

        Order order = orderRepositoryPort.findById(command.orderId())
                .orElseGet(() -> Order.createOrder(customerId));

        Order updatedOrder = order.addItem(
                command.variantId(),
                command.quantity(),
                unitPrice
        );

        return orderRepositoryPort.save(updatedOrder);
    }
}
