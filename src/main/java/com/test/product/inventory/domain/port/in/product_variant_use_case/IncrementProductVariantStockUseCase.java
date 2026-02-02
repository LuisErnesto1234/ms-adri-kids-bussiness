package com.test.product.inventory.domain.port.in.product_variant_use_case;

import com.test.product.inventory.domain.port.in.command.product_variant.IncrementStockProductVariantCommand;

public interface IncrementProductVariantStockUseCase {
    void incrementProductVariantStock(IncrementStockProductVariantCommand command);
}
