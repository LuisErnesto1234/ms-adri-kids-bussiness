package com.adri.kids.orders.domain.port.out.gateway;

import com.adri.kids.orders.domain.model.AppliedCoupon;

public interface PromotionGateway {
    AppliedCoupon getCouponByCode(String code);
}
