package com.adri.kids.catalog.infrastructure.adapter.in.dto.response;

import com.adri.kids.shared.domain.enums.GeneralStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryDetailResponse(UUID id,
                                     String name,
                                     String slug,
                                     String descriptionShort,
                                     String descriptionLong,
                                     String urlImage,
                                     int numberOfOrder,
                                     String colorCode,
                                     boolean isShowMainMenu,
                                     LocalDateTime createdAt,
                                     LocalDateTime updatedAt,
                                     GeneralStatus status) {
}
