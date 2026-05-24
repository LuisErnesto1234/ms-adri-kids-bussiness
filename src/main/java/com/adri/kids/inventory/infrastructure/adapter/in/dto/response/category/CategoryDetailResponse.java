package com.adri.kids.inventory.infrastructure.adapter.in.dto.response.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.adri.kids.shared.domain.enums.GeneralStatus;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Jacksonized
@Builder
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
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
