package com.test.product.inventory.infrastructure.adapter.in.dto.request;

import com.test.product.inventory.application.usecases.createheresection.CreateHeroSectionCommand;
import com.test.product.shared.domain.enums.GeneralStatus;

import java.util.List;

public record CreateHeroSectionRequest(String title, String subtitle, String ctaText,
                                       String ctaUrl, List<String> imageUrls, GeneralStatus status) {

    public CreateHeroSectionCommand toCommand(){
        return new CreateHeroSectionCommand(this.title, this.subtitle, this.ctaText, this.ctaUrl, this.imageUrls, this.status);
    }
}
