package com.test.product.orders.domain.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class CustomerDetailsEvent extends ApplicationEvent {
    private final CompletableFuture<UUID> idCustomer = new CompletableFuture<>();
    private final String username;

    public CustomerDetailsEvent(Object source, String username) {
        super(source);
        this.username = username;
    }
}
