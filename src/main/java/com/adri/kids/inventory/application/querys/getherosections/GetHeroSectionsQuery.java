package com.adri.kids.inventory.application.querys.getherosections;

import an.awesome.pipelinr.Command;

import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.herosection.HeroSectionSummaryResponse;
import com.adri.kids.shared.domain.dtos.PagedResult;

import org.springframework.data.domain.Pageable;

public record GetHeroSectionsQuery(Pageable pageable) implements Command<PagedResult<HeroSectionSummaryResponse>> {
}
