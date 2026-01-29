package com.test.product.inventory.application.service;

import com.test.product.inventory.domain.exception.NotFoundException;
import com.test.product.inventory.domain.model.Category;
import com.test.product.inventory.domain.model.Product;
import com.test.product.inventory.domain.model.details.ProductDetails;
import com.test.product.inventory.domain.port.in.command.product.CreateProductCommand;
import com.test.product.inventory.domain.port.in.product_use_case.CreateProductUseCase;
import com.test.product.inventory.domain.port.out.CategoryRepositoryPort;
import com.test.product.inventory.domain.port.out.ProductRepositoryPort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductService implements CreateProductUseCase {

    private final ProductRepositoryPort repositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;

    public ProductService(ProductRepositoryPort repositoryPort, CategoryRepositoryPort categoryRepositoryPort) {
        this.repositoryPort = repositoryPort;
        this.categoryRepositoryPort = categoryRepositoryPort;
    }

    @Override
    public ProductDetails createProduct(CreateProductCommand command) {

        Product productToSave = Product.createProduct(command.categoryId(), command.name(), command.description(),
                command.basePrice(), command.imageUrl(), command.status());

        Product productSaved = repositoryPort.save(productToSave);

        Category category = categoryRepositoryPort.findById(productSaved.categoryId())
                .orElseThrow(() -> new NotFoundException("La categoria no encuentra para el producto, " + productSaved.id()));

        return new ProductDetails(productSaved.id(), category, productSaved.name(), productSaved.description(),
                productSaved.basePrice(), productSaved.imageUrl(),
                productSaved.productVariants(), productSaved.createdAt(),
                productSaved.updatedAt(), productSaved.status());
    }
}
