package com.test.product.orders.infrastructure.adapter.out.persistence.repository;

import com.test.product.orders.infrastructure.adapter.out.persistence.entity.OrderEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
    @Query("""
                        SELECT o FROM OrderEntity o WHERE o.customerId = :customerId
                        AND o.status = com.test.product.orders.domain.enums.OrderStatus.PENDING
                        AND o.generalStatus = com.test.product.shared.domain.enums.GeneralStatus.ACTIVE
            """)
    Optional<OrderEntity> findAllByCustomerWhereOrderNow(UUID costumerId);

    Page<OrderEntity> findAllByCustomerId(UUID customerId, Pageable pageable);
}
