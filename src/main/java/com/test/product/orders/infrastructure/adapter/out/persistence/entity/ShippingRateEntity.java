package com.test.product.orders.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "shipping_rates")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShippingRateEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String originState;

    @Column(nullable = false)
    private String destinationState;

    @Column(nullable = false)
    private String destinationCity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal rate;

    @Column(name = "estimate_delivery_days", nullable = false)
    private Long estimateDeliveryDays;

    @Column(nullable = false)
    private Instant dateEstimateDelivery;

    @Column(nullable = false, name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(nullable = false, name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

}
