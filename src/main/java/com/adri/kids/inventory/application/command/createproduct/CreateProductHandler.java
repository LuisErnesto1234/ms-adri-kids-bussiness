package com.adri.kids.inventory.application.command.createproduct;

import an.awesome.pipelinr.Command;

import com.adri.kids.inventory.domain.exception.NotFoundException;
import com.adri.kids.inventory.domain.model.Category;
import com.adri.kids.inventory.domain.model.Product;
import com.adri.kids.inventory.domain.model.details.ProductDetails;
import com.adri.kids.inventory.domain.port.out.CategoryRepositoryPort;
import com.adri.kids.inventory.domain.port.out.ProductRepositoryPort;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CreateProductHandler implements Command.Handler<CreateProductCommand, ProductDetails> {

    private final ProductRepositoryPort productRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;

    @Transactional(rollbackFor = Exception.class, timeout = 15, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    @CacheEvict(value = "products_page", allEntries = true)
    @Override
    public ProductDetails handle(CreateProductCommand command) {
        Product productToSave = Product.create(command.categoryId(),
                command.name(), command.description(),
                command.basePrice(), command.imageUrl(), command.isFeatured(), command.status());

        Category category = categoryRepositoryPort.findById(productToSave.categoryId())
                .orElseThrow(() -> new NotFoundException("La categoria no encuentra para el producto, " + productToSave.id()));

        Product productSaved = productRepositoryPort.save(productToSave);

        return new ProductDetails(productSaved.id(), category, productSaved.name(), productSaved.description(),
                productSaved.basePrice(), productSaved.imageUrl(),
                Collections.emptyList(), productSaved.createdAt(),
                productSaved.updatedAt(), productSaved.status(), productSaved.isFeatured());
    }
}
