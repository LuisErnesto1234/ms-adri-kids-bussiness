package com.adri.kids.content.application.herosection.query;

import an.awesome.pipelinr.Command;

import com.adri.kids.content.domain.port.out.HeroSectionRepositoryPort;
import com.adri.kids.content.infrastructure.adapter.in.dto.response.HeroSectionSummaryResponse;

import com.adri.kids.content.infrastructure.adapter.in.mapper.HeroSectionRestMapper;
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