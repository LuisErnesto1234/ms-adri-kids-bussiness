package com.test.product.inventory.domain.model;

import com.test.product.inventory.domain.enums.Status;
import com.test.product.inventory.domain.exception.InsufficientStockException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Slf4j
public record ProductVariant(UUID id, UUID productId, UUID colorId, UUID sizeId, String sku, Integer stockQuantity,
                             BigDecimal priceAdjustment, String imageUrl, Instant createdAt, Instant updatedAt,// 'Sku' a 'sku' por convención
                             Status status) {

    public ProductVariant decreaseStock(Integer quantityRequested) {
        if (this.stockQuantity < quantityRequested) {
            throw new InsufficientStockException("Stock insuficiente para el producto requerido!!");
        }

        Integer newStockValue = stockQuantity - quantityRequested;
        Instant updatedDate = Instant.now();

        return new ProductVariant(id, productId, colorId, sizeId, sku, newStockValue, priceAdjustment, imageUrl, createdAt, updatedDate, status);
    }

    public ProductVariant increaseStock(Integer quantityRequested) {
        if (quantityRequested == null || quantityRequested <= 0) {
            throw new IllegalArgumentException("La cantidad de stock a aumentar no es un valor valido");
        }

        Integer newStockValue = stockQuantity + quantityRequested;
        Instant updatedDate = Instant.now();

        return new ProductVariant(id, productId, colorId, sizeId, sku, newStockValue, priceAdjustment, imageUrl, createdAt, updatedDate, status);
    }

    public static ProductVariant createdProductVariant(UUID productId, UUID colorId, UUID sizeId, String sku, Integer stockQuantity,
                                                       BigDecimal priceAdjustment, String imageUrl) {

        if (productId == null) throw new IllegalArgumentException("productId is null");
        if (colorId == null) throw new IllegalArgumentException("colorId is null");
        if (stockQuantity == null || stockQuantity < 0)
            throw new IllegalArgumentException("La cantidad de stock no puede ser nula o negativa");
        // Se eliminó la validación de priceAdjustment. Un ajuste puede ser negativo (descuento) o cero.
        // Si el precio final no puede ser negativo, esa validación debe hacerse donde el precio base esté disponible.

        return new ProductVariant(UUID.randomUUID(), productId, colorId, sizeId, sku, stockQuantity, priceAdjustment, imageUrl, Instant.now(), Instant.now(), Status.ACTIVE);
    }

    // En tu entidad ProductVariant
    public void associateWithProduct(Product product) {
        // Aquí puedes poner validaciones o lógica de negocio
        if (product == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        // Los records son inmutables. En lugar de modificar, creamos una nueva instancia con el valor actualizado.
        ProductVariant productVariantUpdate = new ProductVariant(id, product.id(), colorId, sizeId, sku, stockQuantity, priceAdjustment, imageUrl,
                createdAt, Instant.now(), status);

        log.info("Producto variante asociado bien, {}", productVariantUpdate);
    }
}
