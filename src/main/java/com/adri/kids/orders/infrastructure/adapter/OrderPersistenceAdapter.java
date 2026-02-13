package com.adri.kids.orders.infrastructure.adapter;

import com.adri.kids.orders.domain.model.Order;
import com.adri.kids.orders.domain.port.out.OrderRepositoryPort;
import com.adri.kids.orders.infrastructure.adapter.out.persistence.mapper.OrderEntityMapper;
import com.adri.kids.orders.infrastructure.adapter.out.persistence.repository.OrderJpaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public Optional<Order> findById(UUID orderId) {
        var orderEntity = orderJpaRepository.findById(orderId);

        if (orderEntity.isPresent()) {
            var orderDomain = orderEntityMapper.toDomain(orderEntity.get());
            return Optional.of(orderDomain);
        }

        return Optional.empty();
    }

    @Override
    public Order save(Order order) {
        var orderEntity = orderEntityMapper.toEntity(order);

        var orderSaved = orderJpaRepository.save(orderEntity);

        return orderEntityMapper.toDomain(orderSaved);
    }

    @Override
    public void delete(Order order) {
        if (orderJpaRepository.existsById(order.id())) {
            var orderToDelete = orderJpaRepository.getReferenceById(order.id());
            orderJpaRepository.delete(orderToDelete);
        }

        throw new IllegalStateException("El proporcionado no coincide con ningun registro actual");
    }

    @Override
    public Optional<Order> findByCustomerId(UUID customerId) {

        var orderEntityFind = orderJpaRepository.findAllByCustomerWhereOrderNow(customerId);

        return orderEntityFind.map(orderEntityMapper::toDomain);
    }

    @Override
    public List<Order> findAllByCostumerId(UUID customerId, int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        return orderJpaRepository.findAllByCustomerId(customerId, pageable)
                .stream()
                .map(orderEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Order> findAll(int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        return orderJpaRepository.findAll(pageable)
                .stream()
                .map(orderEntityMapper::toDomain)
                .toList();
    }
}
