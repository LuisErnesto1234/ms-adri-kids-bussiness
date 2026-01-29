package com.test.product.inventory.domain.port.in.product_variant_use_case;

import com.test.product.inventory.domain.model.details.ProductVariantDetails;
import com.test.product.inventory.domain.port.in.command.product_variant.CreateProductVariantCommand;

public interface CreateProductVariantUseCase {
    ProductVariantDetails createProductVariant(CreateProductVariantCommand command);
}
