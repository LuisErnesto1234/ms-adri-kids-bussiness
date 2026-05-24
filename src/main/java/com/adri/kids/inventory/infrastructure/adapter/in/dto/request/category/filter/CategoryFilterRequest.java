package com.adri.kids.inventory.infrastructure.adapter.in.dto.request.category.filter;

import com.adri.kids.shared.domain.enums.GeneralStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryFilterRequest(
        UUID id,
        String name,
        String slug,
        String colorCode,
        boolean isShowMainMenu,
        LocalDateTime createdFrom,
        LocalDateTime createdTo,
        GeneralStatus status) {
}
