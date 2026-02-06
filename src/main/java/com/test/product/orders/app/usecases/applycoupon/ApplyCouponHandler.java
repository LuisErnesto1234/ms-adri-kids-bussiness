package com.test.product.orders.app.usecases.applycoupon;

import com.test.product.orders.domain.model.AppliedCoupon;
import com.test.product.orders.domain.model.Order;
import com.test.product.orders.domain.port.out.OrderRepositoryPort;
import com.test.product.orders.domain.port.out.gateway.PromotionGateway;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ApplyCouponHandler implements Command.Handler<ApplyCouponCommand, Order> {

    private final OrderRepositoryPort orderRepositoryPort;
    private final PromotionGateway promotionGateway;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, timeout = 500)
    @Override
    public Order handle(ApplyCouponCommand command) {
        var orderFind = orderRepositoryPort.findById(command.orderId());

        if (orderFind.isPresent()) {
            var appliedCoupon = buildAppliedCoupon(command.couponCode());

            if (appliedCoupon != null) {
                var order = orderFind.get().applyCoupon(appliedCoupon);
                return orderRepositoryPort.save(order);
            }

            throw new IllegalStateException("El cupon proporcionado no existe, " + command.couponCode());
        }

        throw new IllegalStateException("No se puede aplicar un cupon a una orden que no existe");
    }

    private AppliedCoupon buildAppliedCoupon(String code) {
        return promotionGateway.getCouponByCode(code);
    }
}
