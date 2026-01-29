package com.test.product.inventory.domain.port.in.command.category;

public record CreateCategoryCommand(String name, String description, String urlImage) {
}
