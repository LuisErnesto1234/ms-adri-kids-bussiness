package com.adri.kids.inventory.application.command.createcategory;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.domain.model.Category;
import com.adri.kids.shared.domain.enums.GeneralStatus;

public record CreateCategoryCommand(String name,
                                    String description,
                                    String urlImage,
                                    GeneralStatus status) implements Command<Category> {
}
