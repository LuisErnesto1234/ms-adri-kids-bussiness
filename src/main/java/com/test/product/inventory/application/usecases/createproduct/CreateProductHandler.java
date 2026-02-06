package com.test.product.inventory.application.usecases.createproduct;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.exception.NotFoundException;
import com.test.product.inventory.domain.model.Category;
import com.test.product.inventory.domain.model.Product;
import com.test.product.inventory.domain.model.details.ProductDetails;
import com.test.product.inventory.domain.port.out.CategoryRepositoryPort;
import com.test.product.inventory.domain.port.out.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProductHandler implements Command.Handler<CreateProductCommand, ProductDetails> {

    private final ProductRepositoryPort productRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public ProductDetails handle(CreateProductCommand command) {
        Product productToSave = Product.createProduct(command.categoryId(),
                command.name(), command.description(),
                command.basePrice(), command.imageUrl(), command.status());

        Product productSaved = productRepositoryPort.save(productToSave);

        Category category = categoryRepositoryPort.findById(productSaved.categoryId())
                .orElseThrow(() -> new NotFoundException("La categoria no encuentra para el producto, " + productSaved.id()));

        return new ProductDetails(productSaved.id(), category, productSaved.name(), productSaved.description(),
                productSaved.basePrice(), productSaved.imageUrl(),
                productSaved.productVariants(), productSaved.createdAt(),
                productSaved.updatedAt(), productSaved.status());
    }
}
