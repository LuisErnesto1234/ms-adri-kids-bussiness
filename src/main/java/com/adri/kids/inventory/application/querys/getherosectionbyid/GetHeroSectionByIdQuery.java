package com.adri.kids.inventory.application.querys.getherosectionbyid;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.herosection.HeroSectionSummaryResponse;

import java.util.UUID;

public record GetHeroSectionByIdQuery(UUID id) implements Command<HeroSectionSummaryResponse> {
}
