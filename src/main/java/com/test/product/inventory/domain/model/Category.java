package com.test.product.inventory.domain.model;

import com.test.product.shared.domain.enums.GeneralStatus;

import java.time.Instant;
import java.util.UUID;

public record Category(UUID id, String name, String description, String urlImage,
                       Instant createdAt, Instant updatedAt,
                       GeneralStatus generalStatus) {

    public Category {
        if (name == null || name.isBlank()) throw new IllegalArgumentException();
        if (description == null || description.isBlank()) throw new IllegalArgumentException();
    }

    public static Category createCategory(String name, String description, String urlImage, GeneralStatus status) {
        if (status == null) {
            status = GeneralStatus.ACTIVE;
        }

        return new Category(UUID.randomUUID(), name, description, urlImage, Instant.now(), Instant.now(), status);
    }

    public Category updateCategory(String name, String description, String urlImage, GeneralStatus status) {
        return new Category(this.id, name, description, urlImage, this.createdAt, Instant.now(), status);
    }

    public Category disable() {
        return new Category(this.id, name, description, urlImage, this.createdAt, Instant.now(), GeneralStatus.INACTIVE);
    }
}
