package com.adri.kids.shared.events.customer;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class CustomerFindByUsernameEvent extends ApplicationEvent {
    private final CompletableFuture<UUID> customerId = new CompletableFuture<>();
    private final String username;

    public CustomerFindByUsernameEvent(Object source, String username) {
        super(source);
        this.username = username;
    }
}
