package com.test.product.inventory.domain.model;

import com.test.product.inventory.domain.enums.InventoryStatus;
import com.test.product.inventory.domain.exception.InsufficientStockException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public record Product(UUID id, UUID categoryId, String name, String description, BigDecimal basePrice, String imageUrl,
                      List<ProductVariant> productVariants,
                      Instant createdAt, Instant updatedAt, InventoryStatus status) {
    
    public boolean hasStock(UUID productVariantId, Integer quantityRequested){
        return productVariants.stream()
                .filter(p -> p.id().equals(productVariantId))
                .findFirst()
                .map(p -> p.stockQuantity() >= quantityRequested)
                .orElse(false);
    }

    public Product{
        log.info("El id de la categoria es, {}", categoryId);

        if (categoryId == null) throw new IllegalArgumentException("categoryId is null");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("El nombre no puede estar en blanco o nulo");
        if (basePrice.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("El precio base no puede ser negativo");

        if (createdAt == null) {
            createdAt = Instant.now();
        }
        
        updatedAt = Instant.now();
    }

    public static Product createProduct(UUID categoryId, String name, String description, BigDecimal basePrice,
                                        String imageUrl, InventoryStatus status){

        log.info("Id de categoria recibido, {}", categoryId);
        // Se pasa una lista vacía al crear un nuevo producto
        return new Product(UUID.randomUUID(), categoryId, name, description, basePrice, imageUrl,
                List.of(), Instant.now(), Instant.now(), status);
    }

    public Product addVariant(ProductVariant variant) {
        if (variant == null) {
            throw new IllegalArgumentException("La variante no puede ser nula.");
        }

        if (!this.id.equals(variant.productId())) {
            throw new IllegalArgumentException("La variante no pertenece a este producto.");
        }

        List<ProductVariant> currentVariants = this.productVariants == null ?
                new ArrayList<>() : this.productVariants;

        if (currentVariants.stream().anyMatch(v -> v.id().equals(variant.id()) || v.sku().equalsIgnoreCase(variant.sku()))) {
            throw new IllegalArgumentException("La variante con ID o SKU ya existe en este producto.");
        }

        List<ProductVariant> updatedVariants = new ArrayList<>(currentVariants);
        updatedVariants.add(variant);

        return new Product(this.id, this.categoryId, this.name, this.description, this.basePrice, this.imageUrl,
                List.copyOf(updatedVariants), this.createdAt, Instant.now(), this.status);
    }
}
