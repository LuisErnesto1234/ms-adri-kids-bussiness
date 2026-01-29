package com.test.product.inventory.domain.port.in.product_variant_use_case;

import com.test.product.inventory.domain.model.ProductVariant;
import com.test.product.inventory.domain.port.in.command.product_variant.DecrementStockProductVariantCommand;

public interface DecrementStockProductVariantUseCase {
    ProductVariant decrementProductVariantStock(DecrementStockProductVariantCommand command);
}
