package com.adri.kids.inventory.application.command.createproductvariant;

import com.adri.kids.inventory.domain.exception.NotFoundException;
import com.adri.kids.inventory.domain.model.Color;
import com.adri.kids.inventory.domain.model.Product;
import com.adri.kids.inventory.domain.model.ProductVariant;
import com.adri.kids.inventory.domain.model.Size;
import com.adri.kids.inventory.domain.model.details.ProductVariantDetails;
import com.adri.kids.inventory.domain.port.out.ColorRepositoryPort;
import com.adri.kids.inventory.domain.port.out.ProductRepositoryPort;
import com.adri.kids.inventory.domain.port.out.ProductVariantRepositoryPort;
import com.adri.kids.inventory.domain.port.out.SizeRepositoryPort;

import lombok.RequiredArgsConstructor;

import an.awesome.pipelinr.Command;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CreateProductVariantHandler implements Command.Handler<CreateProductVariantCommand, ProductVariantDetails> {

    private final ProductVariantRepositoryPort productVariantRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;
    private final ColorRepositoryPort colorRepositoryPort;
    private final SizeRepositoryPort sizeRepositoryPort;
    private final Clock clock;

    @Transactional(rollbackFor = Exception.class, timeout = 20, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @CacheEvict(value = "product_variant_page", allEntries = true)
    @Override
    public ProductVariantDetails handle(CreateProductVariantCommand command) {
        Product productFind = productRepositoryPort.findById(command.productId())
                .orElseThrow(() -> new NotFoundException("El producto no fue encontrado"));

        Color colorFind = colorRepositoryPort.findById(command.colorId())
                .orElseThrow(() -> new NotFoundException("El color no fue encontrado"));

        Size sizeFind = sizeRepositoryPort.findById(command.sizeId())
                .orElseThrow(() -> new NotFoundException("La talla no fue encontrada"));

        String sku = validateSku(productFind, colorFind, command.sku());

        ProductVariant productVariantInit = ProductVariant.createdProductVariant(productFind.id(),
                colorFind.id(), command.sizeId(), sku, command.stockQuantity(), command.priceAdjustment(), command.imageUrl());

        ProductVariant productVariantReady = productVariantInit.associateWithProduct(productFind);

        ProductVariant productVariantSave = productVariantRepositoryPort.save(productVariantReady);

        return buildProductVariantDetails(productVariantSave, productFind, colorFind, sizeFind);
    }

    private String validateSku(Product product, Color color, String sku) {
        if (sku == null || sku.isBlank()) {
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
                "-" +
                color.name().trim().toLowerCase() + "-" +
                Instant.now(clock).getEpochSecond();
    }
}
