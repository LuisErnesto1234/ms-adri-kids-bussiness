package com.test.product.inventory.infrastructure.adapter.listener;

import com.test.product.inventory.application.usecases.getpriceforvariant.GetPriceForVariantIdAndStockCommand;
import com.test.product.inventory.application.usecases.getpriceforvariant.GetPriceForVariantIdAndStockHandler;
import com.test.product.shared.events.CheckStockAndPriceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class InventoryEventListener {

    private final GetPriceForVariantIdAndStockHandler handler;

    @TransactionalEventListener
    public void handle(CheckStockAndPriceEvent event) {
        try {
            GetPriceForVariantIdAndStockCommand command =
                    new GetPriceForVariantIdAndStockCommand(event.getVariantId(), event.getQuantity());

            BigDecimal price = handler.handle(command);
            event.getResult().complete(price);
        } catch (Exception e) {
            event.getResult().completeExceptionally(e);
        }
    }
}
