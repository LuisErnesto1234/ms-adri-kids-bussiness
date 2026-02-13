package com.adri.kids.inventory.infrastructure.adapter.listener;

import com.adri.kids.inventory.application.command.getpriceforvariant.GetPriceForVariantIdAndStockCommand;
import com.adri.kids.inventory.application.command.getpriceforvariant.GetPriceForVariantIdAndStockHandler;
import com.adri.kids.shared.events.inventory.CheckStockAndPriceEvent;
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
