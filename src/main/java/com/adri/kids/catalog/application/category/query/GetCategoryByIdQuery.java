package com.adri.kids.catalog.application.category.query;

import an.awesome.pipelinr.Command;
import com.adri.kids.catalog.domain.model.Category;

import java.util.UUID;

public record GetCategoryByIdQuery(UUID categoryId) implements Command<Category> {
    public GetCategoryByIdQuery {
        if (categoryId == null) {
            throw new IllegalArgumentException("El id de la categoria no puede ser nulo.");
        }
    }
}
