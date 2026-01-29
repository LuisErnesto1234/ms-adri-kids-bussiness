package com.test.product.inventory.domain.port.in.category_use_case;

import com.test.product.inventory.domain.model.Category;
import com.test.product.inventory.domain.port.in.command.category.CreateCategoryCommand;

public interface CreateCategoryUseCase {

    Category createCategory(CreateCategoryCommand command);

}
