package com.test.product.orders.domain.port.out.gateway;

import com.test.product.orders.domain.model.AppliedCoupon;

public interface PromotionGateway {
    AppliedCoupon getCouponByCode(String code);
}
