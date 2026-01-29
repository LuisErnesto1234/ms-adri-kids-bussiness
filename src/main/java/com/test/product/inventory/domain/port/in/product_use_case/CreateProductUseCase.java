package com.test.product.inventory.domain.port.in.product_use_case;

import com.test.product.inventory.domain.model.details.ProductDetails;
import com.test.product.inventory.domain.port.in.command.product.CreateProductCommand;

public interface CreateProductUseCase {
    ProductDetails createProduct(CreateProductCommand command);
}
