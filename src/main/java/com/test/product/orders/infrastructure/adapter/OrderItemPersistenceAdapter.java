package com.test.product.orders.infrastructure.adapter;

import com.test.product.orders.domain.model.OrderItem;
import com.test.product.orders.domain.model.exeception.OrderItemNotFoundException;
import com.test.product.orders.domain.port.out.OrderItemRepositoryPort;
import com.test.product.orders.infrastructure.adapter.out.persistence.mapper.OrderItemEntityMapper;
import com.test.product.orders.infrastructure.adapter.out.persistence.repository.OrderItemJpaRepository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemPersistenceAdapter implements OrderItemRepositoryPort {

    private final OrderItemJpaRepository orderItemJpaRepository;
    private final OrderItemEntityMapper orderItemEntityMapper;

    @Override
    public OrderItem save(OrderItem orderItem) {
        var orderItemEntity = orderItemEntityMapper.toEntity(orderItem);

        var orderItemSaved = orderItemJpaRepository.save(orderItemEntity);

        return orderItemEntityMapper.toDomain(orderItemSaved);
    }

    @Override
    public Optional<OrderItem> findById(UUID id) {
        var orderItemFind = orderItemJpaRepository.findById(id);

        return orderItemFind.map(orderItemEntityMapper::toDomain);
    }

    @Override
    public List<OrderItem> findByOrderId(UUID orderId) {
        return orderItemJpaRepository.findByOrderId(orderId)
                .stream()
                .map(orderItemEntityMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteOrderItem(UUID id) {
        if (orderItemJpaRepository.existsById(id)) {
            orderItemJpaRepository.deleteById(id);
            return;
        }

        throw new OrderItemNotFoundException("El Item de la orden no fue econtrado para su eliminacion");
    }

    @Override
    public Set<OrderItem> findAllById(Set<UUID> ids) {
        return orderItemJpaRepository.findAllById(ids)
                .stream()
                .map(orderItemEntityMapper::toDomain)
                .collect(Collectors.toSet());
    }
}
