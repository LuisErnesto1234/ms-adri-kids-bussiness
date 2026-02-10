package com.test.product.orders.infrastructure.adapter.in.dto.response;

import com.test.product.orders.domain.enums.OrderStatus;
import com.test.product.orders.domain.model.AppliedCoupon;
import com.test.product.orders.domain.model.OrderItem;
import com.test.product.orders.domain.model.PriceBreakdown;
import com.test.product.orders.domain.model.ShippingDetails;
import com.test.product.shared.domain.enums.GeneralStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderResponse(UUID id,
                            UUID customerId,
                            AppliedCoupon appliedCoupon,
                            OrderStatus status,
                            PriceBreakdown priceBreakdown,
                            List<OrderItem> items,
                            ShippingDetails shippingDetails,
                            GeneralStatus generalStatus,
                            Instant createdAt,
                            Instant updatedAt) {
}
