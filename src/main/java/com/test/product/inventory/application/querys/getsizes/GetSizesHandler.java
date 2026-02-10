package com.test.product.inventory.application.querys.getsizes;

import an.awesome.pipelinr.Command;

import com.test.product.inventory.domain.port.out.SizeRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.SizeSummaryResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.SizeRestMapper;
import com.test.product.shared.domain.mapper.PageMapper;
import com.test.product.shared.domain.dtos.PagedResult;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetSizesHandler implements Command.Handler<GetSizesQuery, PagedResult<SizeSummaryResponse>> {

    private final SizeRepositoryPort sizeRepositoryPort;
    private final SizeRestMapper sizeRestMapper;

    @Cacheable(
            value = "sizes_page",
            key = "#query.pageable().pageSize + '-' + #query.pageable().pageNumber + '-' + #query.searchText()",
            unless = "#result.content().empty"
    )
    @Transactional(readOnly = true)
    @Override
    public PagedResult<SizeSummaryResponse> handle(GetSizesQuery query) {

        var sizeEntities = sizeRepositoryPort.findAll(query.pageable());

        var sizeSummaries = sizeEntities.map(sizeRestMapper::toResponse);

        return PageMapper.fromPage(sizeSummaries);
    }
}
