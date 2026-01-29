package com.test.product.inventory.domain.model;

import com.test.product.inventory.domain.enums.Status;

import java.time.Instant;
import java.util.UUID;

public record Color(UUID id, String name, String hexCode,
                    Instant createdAt, Instant updatedAt, Status status) {

    public Color {
        if (name == null) {
            throw new IllegalArgumentException("El nombre no puede estar en blanco o nulo");
        }

        if (hexCode == null) {
            throw new IllegalArgumentException("El codigo hexadecimal no puede estar en blanco o nulo");
        }

        if (status == null) {
            status = Status.ACTIVE;
        }
    }

    public static Color createColor(String name, String hexCode, Status status) {
        return new Color(UUID.randomUUID(), name, hexCode, Instant.now(), Instant.now(), status);
    }

    public static Color updateColor(UUID id, String name, String hexCode, Instant createdAt, Status status) {
        return new Color(id, name, hexCode, createdAt, Instant.now(), status);
    }

}
