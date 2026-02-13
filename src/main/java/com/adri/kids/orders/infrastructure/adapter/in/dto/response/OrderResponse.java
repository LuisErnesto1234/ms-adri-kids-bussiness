package com.adri.kids.orders.infrastructure.adapter.in.dto.response;

import com.adri.kids.orders.domain.enums.OrderStatus;
import com.adri.kids.orders.domain.model.AppliedCoupon;
import com.adri.kids.orders.domain.model.OrderItem;
import com.adri.kids.orders.domain.model.PriceBreakdown;
import com.adri.kids.orders.domain.model.ShippingDetails;
import com.adri.kids.shared.domain.enums.GeneralStatus;

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
