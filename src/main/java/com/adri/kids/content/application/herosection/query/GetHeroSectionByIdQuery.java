package com.adri.kids.content.application.herosection.query;

import an.awesome.pipelinr.Command;
import com.adri.kids.content.infrastructure.adapter.in.dto.response.HeroSectionSummaryResponse;

import java.util.UUID;

public record GetHeroSectionByIdQuery(UUID id) implements Command<HeroSectionSummaryResponse> {
}
