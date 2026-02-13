package com.test.product.inventory.application.querys.getherosections;

import an.awesome.pipelinr.Command;

import com.test.product.inventory.infrastructure.adapter.in.dto.response.herosection.HeroSectionSummaryResponse;
import com.test.product.shared.domain.dtos.PagedResult;

import org.springframework.data.domain.Pageable;

public record GetHeroSectionsQuery(Pageable pageable) implements Command<PagedResult<HeroSectionSummaryResponse>> {
}
