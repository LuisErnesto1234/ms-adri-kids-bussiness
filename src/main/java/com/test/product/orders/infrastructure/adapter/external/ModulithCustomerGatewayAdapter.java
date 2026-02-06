package com.test.product.orders.infrastructure.adapter.external;

import com.test.product.orders.domain.events.CustomerDetailsEvent;
import com.test.product.orders.domain.port.out.gateway.CustomerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class ModulithCustomerGatewayAdapter implements CustomerGateway {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public UUID getCustomerIdByUsername(String username) {

        var event = new CustomerDetailsEvent(this, username);
        eventPublisher.publishEvent(event);
        try {
            return event.getIdCustomer().get(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException("Failed to retrieve customer ID", e);
        }
    }
}
