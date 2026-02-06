package com.test.product.orders.infrastructure.adapter.out.persistence.repository;

import com.test.product.orders.infrastructure.adapter.out.persistence.entity.ShippingRateEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShippingRateJpaRepository extends JpaRepository<ShippingRateEntity, UUID> {
}
