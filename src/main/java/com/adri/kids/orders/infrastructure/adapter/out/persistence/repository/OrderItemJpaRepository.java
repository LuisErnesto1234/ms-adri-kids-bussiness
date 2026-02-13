package com.adri.kids.orders.infrastructure.adapter.out.persistence.repository;

import com.adri.kids.orders.infrastructure.adapter.out.persistence.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, UUID> {

    @Query("""
            SELECT oi FROM OrderItemEntity oi WHERE oi.order.id = :orderId
            """)
    List<OrderItemEntity> findByOrderId(UUID orderId);

}
