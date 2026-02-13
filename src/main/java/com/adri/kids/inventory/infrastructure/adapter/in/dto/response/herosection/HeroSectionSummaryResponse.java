package com.adri.kids.inventory.infrastructure.adapter.in.dto.response.herosection;

import com.fasterxml.jackson.annotation.*;
import com.adri.kids.shared.domain.enums.GeneralStatus;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(visible = true, include = JsonTypeInfo.As.PROPERTY, use = JsonTypeInfo.Id.CLASS, property = "@class")
public record HeroSectionSummaryResponse(UUID id,
                                         String title,
                                         String subtitle,
                                         String ctaText,
                                         String ctaUrl,
                                         List<String> imageUrls,
                                         Integer displayOrder,
                                         GeneralStatus status) {
}
