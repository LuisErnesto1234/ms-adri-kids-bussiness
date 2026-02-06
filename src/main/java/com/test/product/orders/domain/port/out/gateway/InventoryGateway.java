package com.test.product.orders.domain.port.out.gateway;

import java.math.BigDecimal;
import java.util.UUID;

public interface InventoryGateway {
    BigDecimal getPriceForVariantAndHaveStock(UUID variantId, int quantity);
}
