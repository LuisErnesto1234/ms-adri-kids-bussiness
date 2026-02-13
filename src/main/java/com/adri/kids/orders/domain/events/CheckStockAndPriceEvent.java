package com.adri.kids.orders.domain.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class CheckStockAndPriceEvent extends ApplicationEvent {

    private final UUID variantId;
    private final int quantity;
    private final CompletableFuture<BigDecimal> result = new CompletableFuture<>();

    public CheckStockAndPriceEvent(Object source, UUID variantId, int quantity) {
        super(source);
        this.variantId = variantId;
        this.quantity = quantity;
    }
}
