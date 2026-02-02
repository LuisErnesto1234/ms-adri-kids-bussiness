package com.test.product.inventory.application.service;

import com.test.product.inventory.domain.exception.NotFoundException;
import com.test.product.inventory.domain.model.Color;
import com.test.product.inventory.domain.model.Product;
import com.test.product.inventory.domain.model.ProductVariant;
import com.test.product.inventory.domain.model.Size;
import com.test.product.inventory.domain.model.details.ProductVariantDetails;
import com.test.product.inventory.domain.port.in.command.product_variant.CreateProductVariantCommand;
import com.test.product.inventory.domain.port.in.command.product_variant.DecrementStockProductVariantCommand;
import com.test.product.inventory.domain.port.in.command.product_variant.IncrementStockProductVariantCommand;
import com.test.product.inventory.domain.port.in.product_variant_use_case.CreateProductVariantUseCase;
import com.test.product.inventory.domain.port.in.product_variant_use_case.DecrementStockProductVariantUseCase;
import com.test.product.inventory.domain.port.in.product_variant_use_case.IncrementProductVariantStockUseCase;
import com.test.product.inventory.domain.port.out.ColorRepositoryPort;
import com.test.product.inventory.domain.port.out.ProductRepositoryPort;
import com.test.product.inventory.domain.port.out.ProductVariantRepositoryPort;
import com.test.product.inventory.domain.port.out.SizeRepositoryPort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.time.Instant;

public class ProductVariantService implements CreateProductVariantUseCase, IncrementProductVariantStockUseCase,
        DecrementStockProductVariantUseCase {

    private final ProductVariantRepositoryPort productVariantRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;
    private final ColorRepositoryPort colorRepositoryPort;
    private final SizeRepositoryPort sizeRepositoryPort;

    public ProductVariantService(ProductVariantRepositoryPort productVariantRepositoryPort,
                                 ProductRepositoryPort productRepositoryPort,
                                 ColorRepositoryPort colorRepositoryPort,
                                 SizeRepositoryPort sizeRepositoryPort) {
        
        this.productVariantRepositoryPort = productVariantRepositoryPort;
        this.productRepositoryPort = productRepositoryPort;
        this.colorRepositoryPort = colorRepositoryPort;
        this.sizeRepositoryPort = sizeRepositoryPort;
    }

    @Override
    public ProductVariantDetails createProductVariant(CreateProductVariantCommand command) {
        Product productFind = productRepositoryPort.findById(command.productId())
                .orElseThrow(() -> new NotFoundException("El producto no fue encontrado"));

        Color colorFind = colorRepositoryPort.findById(command.colorId())
                .orElseThrow(() -> new NotFoundException("El color no fue encontrado"));

        Size sizeFind = sizeRepositoryPort.findById(command.sizeId())
                .orElseThrow(() -> new NotFoundException("La talla no fue encontrada"));

        String sku = validateSku(productFind, colorFind, command.sku());

        ProductVariant productVariantCreate = ProductVariant.createdProductVariant(productFind.id(),
                colorFind.id(), command.sizeId(), sku, command.stockQuantity(), command.priceAdjustment(), command.imageUrl());

        productVariantCreate.associateWithProduct(productFind);

        ProductVariant productVariantSave = productVariantRepositoryPort.save(productVariantCreate);

        return buildProductVariantDetails(productVariantSave, productFind, colorFind, sizeFind);
    }

    private String validateSku (Product product, Color color, String sku){
        if (sku == null || sku.isBlank()){
            return createDynamicSku(product, color);
        }
        return sku;
    }

    private ProductVariantDetails buildProductVariantDetails(ProductVariant productVariant, Product product,
                                                             Color color, Size size) {

        return new ProductVariantDetails(productVariant.id(), product, color, size, productVariant.sku(),
                productVariant.stockQuantity(), productVariant.priceAdjustment(),
                productVariant.imageUrl(), productVariant.createdAt(),
                productVariant.updatedAt(), productVariant.status());
    }

    private String createDynamicSku(Product product, Color color) {
        return product.name().trim().toLowerCase() +
                "_" +
                color.name().trim().toLowerCase() +
                Instant.now().getEpochSecond();
    }

    @Override
    public void incrementProductVariantStock(IncrementStockProductVariantCommand command) {
        try {
            ProductVariant productVariantFind = productVariantRepositoryPort.findById(command.productVariantId())
                    .orElseThrow(() -> new NotFoundException("El producto no fue encontrado, para su incremento de stock"));

            productVariantFind.increaseStock(command.quantity());

        } catch (ObjectOptimisticLockingFailureException e){
            throw new NotFoundException("El stock fue modificado por otro usuario mientras comprabas. Por favor, intenta de nuevo.", e);
        }
    }

    @Override
    public ProductVariant decrementProductVariantStock(DecrementStockProductVariantCommand command) {
        try {
            ProductVariant productVariantFind = productVariantRepositoryPort.findById(command.productVariantId())
                    .orElseThrow(() -> new NotFoundException("El producto no fue encontrado, para su decremento de stock"));

            productVariantFind.decreaseStock(command.stock());

            return productVariantRepositoryPort.save(productVariantFind);
        } catch (ObjectOptimisticLockingFailureException e){
            throw new NotFoundException("El stock ha sido modificado por otro usuario mientras comprabas. Por favor, intenta de nuevo");
        }
    }
}
