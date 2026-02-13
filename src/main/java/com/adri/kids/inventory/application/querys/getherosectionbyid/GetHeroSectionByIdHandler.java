package com.adri.kids.inventory.application.querys.getherosectionbyid;

import an.awesome.pipelinr.Command;

import com.adri.kids.inventory.domain.port.out.HeroSectionRepositoryPort;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.herosection.HeroSectionSummaryResponse;

import com.adri.kids.inventory.infrastructure.adapter.in.mapper.HeroSectionRestMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetHeroSectionByIdHandler implements Command.Handler<GetHeroSectionByIdQuery, HeroSectionSummaryResponse> {

    private final HeroSectionRepositoryPort heroSectionRepositoryPort;
    private final HeroSectionRestMapper heroSectionRestMapper;

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, timeout = 10)
    @Cacheable(value = "hero_section", key = "'hero_section:' + #query.id", unless = "#result == null")
    @Override
    public HeroSectionSummaryResponse handle(GetHeroSectionByIdQuery query) {

        var domain = heroSectionRepositoryPort.findById(query.id())
                .orElseThrow(() -> new RuntimeException("La section del banner principal no se encontró"));

        return heroSectionRestMapper.toResponse(domain);
    }
}