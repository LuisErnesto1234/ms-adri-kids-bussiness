package com.test.product.inventory.application.usecases.createcategory;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.model.Category;
import com.test.product.shared.domain.enums.GeneralStatus;

public record CreateCategoryCommand(String name,
                                    String description,
                                    String urlImage,
                                    GeneralStatus status) implements Command<Category> {
}
