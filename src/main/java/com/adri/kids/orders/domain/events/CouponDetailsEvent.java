package com.adri.kids.orders.domain.events;

import com.adri.kids.orders.domain.enums.DiscountType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class CouponDetailsEvent extends ApplicationEvent {

    private final CompletableFuture<UUID> id = new CompletableFuture<>();            // El ID original del cupón
    private final String code;         // Ej: "VERANO2026"
    private final CompletableFuture<DiscountType> type = new CompletableFuture<>(); // PERCENTAGE o FIXED
    private final CompletableFuture<BigDecimal> value = new CompletableFuture<>();

    public CouponDetailsEvent(Object source, String code) {
        super(source);
        this.code = code;

    }
}
