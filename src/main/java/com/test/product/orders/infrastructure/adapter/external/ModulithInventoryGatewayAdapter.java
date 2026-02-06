package com.test.product.orders.infrastructure.adapter.external;

import com.test.product.orders.domain.events.CheckStockAndPriceEvent;
import com.test.product.orders.domain.port.out.gateway.InventoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class ModulithInventoryGatewayAdapter implements InventoryGateway {

    private final ApplicationEventPublisher events;

    @Override
    public BigDecimal getPriceForVariantAndHaveStock(UUID variantId, int quantity) {
        var event = new CheckStockAndPriceEvent(this, variantId, quantity);
        events.publishEvent(event);
        try {
            return event.getResult().get(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException("Failed to retrieve price and stock information", e);
        }
    }
}
