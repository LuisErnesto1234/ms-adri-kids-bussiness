package com.adri.kids.inventory.infrastructure.adapter.in.dto.request;

import com.adri.kids.inventory.application.command.category.createcategory.CreateCategoryCommand;

public record CreateCategoryRequest(String name,
                                    String slug,
                                    String descriptionShort,
                                    String descriptionLong,
                                    String urlImage,
                                    int numberOfOrder,
                                    String colorCode,
                                    boolean isPublishImmediately,
                                    boolean isShowMainMenu) {

    public CreateCategoryCommand toCommand() {
        return new CreateCategoryCommand(
                this.name,
                this.slug,
                this.descriptionShort,
                this.descriptionLong,
                this.urlImage,
                this.numberOfOrder,
                this.colorCode,
                this.isPublishImmediately,
                this.isShowMainMenu);
    }
}
