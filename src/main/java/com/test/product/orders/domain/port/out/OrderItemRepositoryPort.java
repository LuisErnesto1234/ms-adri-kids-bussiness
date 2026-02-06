package com.test.product.orders.domain.port.out;

import com.test.product.orders.domain.model.OrderItem;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface OrderItemRepositoryPort {
    OrderItem save(OrderItem orderItem);

    Optional<OrderItem> findById(UUID id);

    List<OrderItem> findByOrderId(UUID orderId);

    void deleteOrderItem(UUID id);

    Set<OrderItem> findAllById(Set<UUID> ids);
}
