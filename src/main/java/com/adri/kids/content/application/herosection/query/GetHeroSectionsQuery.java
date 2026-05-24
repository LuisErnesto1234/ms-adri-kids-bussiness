package com.adri.kids.content.application.herosection.query;

import an.awesome.pipelinr.Command;

import com.adri.kids.content.infrastructure.adapter.in.dto.response.HeroSectionSummaryResponse;
import com.adri.kids.shared.domain.dtos.PagedResult;

import org.springframework.data.domain.Pageable;

public record GetHeroSectionsQuery(Pageable pageable) implements Command<PagedResult<HeroSectionSummaryResponse>> {
}
