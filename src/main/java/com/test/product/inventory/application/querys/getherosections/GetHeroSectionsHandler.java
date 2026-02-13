package com.test.product.inventory.application.querys.getherosections;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.port.out.HeroSectionRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.herosection.HeroSectionSummaryResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.HeroSectionRestMapper;
import com.test.product.shared.domain.dtos.PagedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetHeroSectionsHandler implements Command.Handler<GetHeroSectionsQuery, PagedResult<HeroSectionSummaryResponse>> {

    private final HeroSectionRepositoryPort heroSectionRepositoryPort;
    private final HeroSectionRestMapper heroSectionRestMapper;

    @Transactional(readOnly = true, timeout = 10, isolation = Isolation.READ_COMMITTED)
    @Cacheable(
            value = "hero_section_page",
            key = "'hero_section:' + #query.pageable().pageNumber + '-' + #query.pageable().pageSize + '-' + #query.pageable().sort",
            unless = "#result.content.empty"
    )
    @Override
    public PagedResult<HeroSectionSummaryResponse> handle(GetHeroSectionsQuery query) {

        var domainPage = heroSectionRepositoryPort.findAll(query.pageable());

        var inmutableList = domainPage.getContent().stream()
                .map(heroSectionRestMapper::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));

        return new PagedResult<>(
                inmutableList,
                query.pageable().getPageNumber(),
                query.pageable().getPageSize(),
                domainPage.getTotalElements(),
                domainPage.getTotalPages()
        );
    }
}
