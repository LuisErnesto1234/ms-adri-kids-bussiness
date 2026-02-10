package com.test.product.inventory.domain.model;

import com.test.product.inventory.domain.enums.InventoryStatus;

import java.time.Instant;
import java.util.UUID;

public record Color(UUID id, String name, String hexCode,
                    Instant createdAt, Instant updatedAt, InventoryStatus status) {

    public Color {
        if (name == null) {
            throw new IllegalArgumentException("El nombre no puede estar en blanco o nulo");
        }

        if (hexCode == null) {
            throw new IllegalArgumentException("El codigo hexadecimal no puede estar en blanco o nulo");
        }

        if (status == null) {
            status = InventoryStatus.AVAILABLE;
        }
    }

    public static Color createColor(String name, String hexCode, InventoryStatus status) {
        return new Color(UUID.randomUUID(), name, hexCode, Instant.now(), Instant.now(), status);
    }

    public static Color updateColor(UUID id, String name, String hexCode, Instant createdAt, InventoryStatus status) {
        return new Color(id, name, hexCode, createdAt, Instant.now(), status);
    }

}
