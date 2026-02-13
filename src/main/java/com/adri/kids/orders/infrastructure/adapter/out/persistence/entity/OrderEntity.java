package com.adri.kids.orders.infrastructure.adapter.out.persistence.entity;

import com.adri.kids.orders.domain.enums.OrderStatus;

import com.adri.kids.orders.infrastructure.adapter.out.persistence.entity.embeddable.AppliedCouponEmbeddable;
import com.adri.kids.orders.infrastructure.adapter.out.persistence.entity.embeddable.PriceBreakdownEmbeddable;
import com.adri.kids.orders.infrastructure.adapter.out.persistence.entity.embeddable.ShippingDetailsEmbeddable;
import com.adri.kids.shared.domain.enums.GeneralStatus;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    private UUID id;

    @Column(nullable = false, name = "costumer_id")
    private UUID customerId;

    @Embedded
    private AppliedCouponEmbeddable appliedCoupon;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private PriceBreakdownEmbeddable priceBreakdown;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItemEntity> items;

    @Embedded
    private ShippingDetailsEmbeddable shippingDetails;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_general", nullable = false)
    private GeneralStatus generalStatus;
}