package com.test.product.inventory.domain.model;

import java.util.UUID;

public record Category(UUID id, String name, String description, String urlImage) {

    public Category{
        if (name == null || name.isBlank()) throw new  IllegalArgumentException();
        if (description == null || description.isBlank()) throw new IllegalArgumentException();
    }

    public static Category createCategory(String name, String description, String urlImage) {
        return new Category(UUID.randomUUID(), name, description, urlImage);
    }
}
