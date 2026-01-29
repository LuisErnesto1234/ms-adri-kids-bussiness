package com.test.product.inventory.domain.port.in.product_variant_use_case;

import com.test.product.inventory.domain.model.ProductVariant;
import com.test.product.inventory.domain.port.in.command.product_variant.IncrementStockProductVariantCommand;

public interface IncrementProductVariantStockUseCase {
    ProductVariant incrementProductVariantStock(IncrementStockProductVariantCommand command);
}
