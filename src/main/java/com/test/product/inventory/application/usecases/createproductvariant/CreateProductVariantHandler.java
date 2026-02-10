package com.test.product.inventory.application.usecases.createproductvariant;

import com.test.product.inventory.domain.exception.NotFoundException;
import com.test.product.inventory.domain.model.Color;
import com.test.product.inventory.domain.model.Product;
import com.test.product.inventory.domain.model.ProductVariant;
import com.test.product.inventory.domain.model.Size;
import com.test.product.inventory.domain.model.details.ProductVariantDetails;
import com.test.product.inventory.domain.port.out.ColorRepositoryPort;
import com.test.product.inventory.domain.port.out.ProductRepositoryPort;
import com.test.product.inventory.domain.port.out.ProductVariantRepositoryPort;
import com.test.product.inventory.domain.port.out.SizeRepositoryPort;

import lombok.RequiredArgsConstructor;

import an.awesome.pipelinr.Command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateProductVariantHandler implements Command.Handler<CreateProductVariantCommand, ProductVariantDetails> {

    private final ProductVariantRepositoryPort productVariantRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;
    private final ColorRepositoryPort colorRepositoryPort;
    private final SizeRepositoryPort sizeRepositoryPort;

    @Transactional(rollbackFor = Exception.class, timeout = 10, propagation = Propagation.REQUIRES_NEW)
    @CacheEvict(value = "product_variant_page", allEntries = true)
    @Override
    public ProductVariantDetails handle(CreateProductVariantCommand command) {
        Product productFind = productRepositoryPort.findById(command.productId())
                .orElseThrow(() -> new NotFoundException("El producto no fue encontrado"));

        log.info("LLego el producto de manera excelente, {}", productFind.name());

        Color colorFind = colorRepositoryPort.findById(command.colorId())
                .orElseThrow(() -> new NotFoundException("El color no fue encontrado"));

        Size sizeFind = sizeRepositoryPort.findById(command.sizeId())
                .orElseThrow(() -> new NotFoundException("La talla no fue encontrada"));

        String sku = validateSku(productFind, colorFind, command.sku());

        ProductVariant productVariantInit = ProductVariant.createdProductVariant(productFind.id(),
                colorFind.id(), command.sizeId(), sku, command.stockQuantity(), command.priceAdjustment(), command.imageUrl());

        // 4. Lógica de asociación (Devuelve la nueva instancia inmutable)
        // NOTA: Si ya pasaste el productFind.id() en el constructor de arriba,
        // este paso quizás solo sea para agregar a la lista del padre en memoria.
        ProductVariant productVariantReady = productVariantInit.associateWithProduct(productFind);

        // 5. Guardado
        // Si tienes Cascade.ALL en Product, basta con: productRepositoryPort.save(productFind);
        // Si no, guardamos la variante:
        ProductVariant productVariantSave = productVariantRepositoryPort.save(productVariantReady);

        // Solo guardamos producto si modificamos algo en él, si solo tocamos la lista y no hay cascade,
        // esto sobra.
        // productRepositoryPort.save(productFind);

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
                "_" +
                color.name().trim().toLowerCase() +
                Instant.now().getEpochSecond();
    }
}
