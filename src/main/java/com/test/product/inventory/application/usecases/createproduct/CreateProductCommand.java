package com.test.product.inventory.application.usecases.createproduct;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.enums.Status;
import com.test.product.inventory.domain.model.details.ProductDetails;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductCommand(UUID categoryId, String name,
                                   String description, BigDecimal basePrice,
                                   String imageUrl, Status status) implements Command<ProductDetails> {
}
