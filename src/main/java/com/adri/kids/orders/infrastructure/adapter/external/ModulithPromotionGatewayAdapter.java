package com.adri.kids.orders.infrastructure.adapter.external;

import com.adri.kids.orders.domain.events.CouponDetailsEvent;
import com.adri.kids.orders.domain.model.AppliedCoupon;
import com.adri.kids.orders.domain.port.out.gateway.PromotionGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class ModulithPromotionGatewayAdapter implements PromotionGateway {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public AppliedCoupon getCouponByCode(String code) {
        var event = new CouponDetailsEvent(this, code);
        eventPublisher.publishEvent(event);
        try {
            return new AppliedCoupon(event.getId().get(), event.getCode(), event.getType().get(), event.getValue().get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
