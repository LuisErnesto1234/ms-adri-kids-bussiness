package com.test.product.inventory.application.usecases.createcategory;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.model.Category;

public record CreateCategoryCommand(String name,
                                    String description,
                                    String urlImage) implements Command<Category> {
}
