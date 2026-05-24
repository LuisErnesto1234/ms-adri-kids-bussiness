package com.adri.kids.inventory.infrastructure.adapter.in.dto.request;

import com.adri.kids.inventory.application.command.category.updatecategory.UpdateCategoryCommand;
import com.adri.kids.shared.domain.enums.GeneralStatus;

import java.util.UUID;

public record UpdateCategoryRequest(UUID categoryId,
                                    String name,
                                    String descriptionShort,
                                    String descriptionLong,
                                    String urlImage,
                                    int numberOfOrder,
                                    String colorCode,
                                    boolean isShowMainMenu,
                                    GeneralStatus status) {

    public UpdateCategoryCommand toCommand(UUID categoryId) {
        return new UpdateCategoryCommand(
                categoryId,
                name,
                descriptionShort,
                descriptionLong,
                urlImage,
                numberOfOrder,
                colorCode,
                isShowMainMenu,
                status
        );
    }

}
