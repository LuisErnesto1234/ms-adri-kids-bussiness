package com.adri.kids.inventory.domain.model;

import com.adri.kids.inventory.domain.enums.InventoryStatus;
import com.adri.kids.inventory.domain.enums.TypeProduct;

import java.time.Instant;
import java.util.UUID;

public record Size(UUID id, String name, TypeProduct type, String sortOrder,
                   Instant createdAt, Instant updatedAt,
                   InventoryStatus status) {

    public Size {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar en blanco o nulo");
        }

        if (type == null) {
            throw new IllegalArgumentException("El tipo no puede estar en blanco o nulo");
        }

        if (sortOrder == null || sortOrder.isBlank()) {
            throw new IllegalArgumentException("El orden no puede estar en blanco o nulo");
        }

        if (createdAt == null || createdAt.isAfter(Instant.now())) {
            createdAt = Instant.now();
        }

    }

    public static Size createSize(String name, TypeProduct type, String sortOrder, InventoryStatus status) {
        return new Size(UUID.randomUUID(), name, type, sortOrder, Instant.now(), Instant.now(), status);
    }
}
