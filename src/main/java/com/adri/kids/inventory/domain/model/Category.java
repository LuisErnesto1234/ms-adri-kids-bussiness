package com.adri.kids.inventory.domain.model;

import com.adri.kids.shared.domain.enums.GeneralStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record Category(UUID id,
                       String name,
                       String slug,
                       String descriptionShort,
                       String descriptionLong,
                       String urlImage,
                       int numberOfOrder,
                       String colorCode,
                       Boolean isShowMainMenu,
                       LocalDateTime createdAt,
                       LocalDateTime updatedAt,
                       GeneralStatus status,
                       List<Product> products) {

    public Category {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }
        if (slug == null || slug.isBlank()) {
            throw new IllegalArgumentException("Slug cannot be null or blank.");
        }
        if (descriptionShort == null || descriptionShort.isBlank()) {
            throw new IllegalArgumentException("Short description cannot be null or blank.");
        }

        if (descriptionLong == null || descriptionLong.isBlank()) {
            descriptionLong = this.descriptionShort();
        }

        if (numberOfOrder < 0) {
            throw new IllegalArgumentException("Order number must be non-negative.");
        }
        if (status == null) {
            status = GeneralStatus.ACTIVE;
        }

        if (colorCode == null) {
            throw new IllegalArgumentException("Color code of theme cannot be null.");
        }

        if (products == null) {
            products = new ArrayList<>();
        }
    }

    public static Category createCategory(String name, String slug,
                                          String descriptionShort,
                                          String descriptionLong,
                                          String urlImage,
                                          int numberOfOrder,
                                          String colorCode,
                                          boolean isShowMainMenu) {

        return new Category(UUID.randomUUID(), name,
                slug, descriptionShort,
                descriptionLong, urlImage,
                numberOfOrder,
                colorCode,
                isShowMainMenu,
                LocalDateTime.now(),
                LocalDateTime.now(),
                GeneralStatus.ACTIVE,
                new ArrayList<>());
    }

    public Category updateCategory(String name,
                                   String descriptionShort,
                                   String descriptionLong,
                                   String urlImage, int numberOfOrder,
                                   String colorCode,
                                   boolean isShowMainMenu,
                                   GeneralStatus status) {

        var updateCategory = new Category(this.id, name,
                this.slug, descriptionShort,
                descriptionLong, urlImage,
                numberOfOrder,
                colorCode,
                isShowMainMenu,
                this.createdAt,
                LocalDateTime.now(), status, this.products);

        if (this.equals(updateCategory)) {
            return this;
        }

        return updateCategory;
    }

    public Category deactivate() {
        if (this.status == GeneralStatus.INACTIVE) {
            return this;
        }

        return new Category(this.id, this.name,
                this.slug,
                this.descriptionShort,
                this.descriptionLong,
                this.urlImage,
                this.numberOfOrder, this.colorCode,
                this.isShowMainMenu,
                this.createdAt, LocalDateTime.now(),
                GeneralStatus.INACTIVE, this.products);
    }

    public Category activate() {
        if (this.status == GeneralStatus.ACTIVE) {
            return this;
        }

        return new Category(this.id, this.name,
                this.slug,
                this.descriptionShort,
                this.descriptionLong,
                this.urlImage,
                this.numberOfOrder, this.colorCode,
                this.isShowMainMenu,
                this.createdAt,
                LocalDateTime.now(),
                GeneralStatus.ACTIVE, this.products);
    }
}
