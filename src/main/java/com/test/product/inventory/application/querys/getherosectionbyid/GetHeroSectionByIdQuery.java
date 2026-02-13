package com.test.product.inventory.application.querys.getherosectionbyid;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.herosection.HeroSectionSummaryResponse;

import java.util.UUID;

public record GetHeroSectionByIdQuery(UUID id) implements Command<HeroSectionSummaryResponse> {
}
