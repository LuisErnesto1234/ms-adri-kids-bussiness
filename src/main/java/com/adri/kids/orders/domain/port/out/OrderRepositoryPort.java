package com.adri.kids.orders.domain.port.out;

import com.adri.kids.orders.domain.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryPort {
    Optional<Order> findById(UUID orderId);

    Order save(Order order);

    void delete(Order order);

    Optional<Order> findByCustomerId(UUID customerId);

    List<Order> findAllByCostumerId(UUID customerId, int page, int size, String order);

    List<Order> findAll(int page, int size, String order);
}
