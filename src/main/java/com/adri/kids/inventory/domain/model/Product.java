package com.adri.kids.inventory.domain.model;

import com.adri.kids.inventory.domain.enums.InventoryStatus;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public record Product(UUID id, UUID categoryId, String name, String description,
                      BigDecimal basePrice, String imageUrl,
                      List<ProductVariant> productVariants, Integer countVariants,
                      Boolean isFeatured, Instant createdAt, Instant updatedAt,
                      InventoryStatus status) {

    public boolean hasStock(UUID productVariantId, Integer quantityRequested) {
        return productVariants.stream()
                .filter(p -> p.id().equals(productVariantId))
                .findFirst()
                .map(p -> p.stockQuantity() >= quantityRequested)
                .orElse(false);
    }

    public Product {
        if (categoryId == null) throw new IllegalArgumentException("El id de la categoria no puede ser nulo");

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("El nombre no puede estar en blanco o nulo");

        if (basePrice.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("El precio base no puede ser negativo");

        if (createdAt == null) createdAt = Instant.now();


        updatedAt = Instant.now();
    }

    public static Product create(UUID categoryId, String name, String description, BigDecimal basePrice,
                                 String imageUrl, Boolean isFeatured, InventoryStatus status) {

        log.info("Id de categoria recibido, {}", categoryId);
        // Se pasa una lista vacía al crear un nuevo producto
        return new Product(UUID.randomUUID(), categoryId, name, description, basePrice,
                imageUrl, List.of(), 0, isFeatured, Instant.now(), Instant.now(), status);
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
        Integer newCountVariants = countVariants + 1;

        return new Product(this.id, this.categoryId, this.name, this.description, this.basePrice, this.imageUrl,
                List.copyOf(updatedVariants), newCountVariants, this.isFeatured, this.createdAt, Instant.now(), this.status);
    }

    public Product update(UUID categoryId, String name, String description, BigDecimal basePrice,
                          String imageUrl, Boolean isFeatured, InventoryStatus status) {
        return new Product(this.id, categoryId, name, description, basePrice, imageUrl,
                this.productVariants, this.countVariants, isFeatured, this.createdAt, Instant.now(), status);
    }
}
