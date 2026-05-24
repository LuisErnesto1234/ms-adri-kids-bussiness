package com.adri.kids.inventory.application.querys.category.getcategorybyid;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.domain.model.Category;

import java.util.UUID;

public record GetCategoryByIdQuery(UUID categoryId) implements Command<Category> {
    public GetCategoryByIdQuery {
        if (categoryId == null) {
            throw new IllegalArgumentException("El id de la categoria no puede ser nulo.");
        }
    }
}
