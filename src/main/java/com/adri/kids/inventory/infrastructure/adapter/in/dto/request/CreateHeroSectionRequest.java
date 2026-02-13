package com.adri.kids.inventory.infrastructure.adapter.in.dto.request;

import com.adri.kids.inventory.application.command.createheresection.CreateHeroSectionCommand;
import com.adri.kids.shared.domain.enums.GeneralStatus;

import java.util.List;

public record CreateHeroSectionRequest(String title, String subtitle, String ctaText,
                                       String ctaUrl, List<String> imageUrls, GeneralStatus status) {

    public CreateHeroSectionCommand toCommand(){
        return new CreateHeroSectionCommand(this.title, this.subtitle, this.ctaText, this.ctaUrl, this.imageUrls, this.status);
    }
}
