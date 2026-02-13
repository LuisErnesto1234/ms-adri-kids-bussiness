package com.adri.kids.inventory.application.querys.getsizes;

import an.awesome.pipelinr.Command;

import com.adri.kids.inventory.domain.port.out.SizeRepositoryPort;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.size.SizeSummaryResponse;
import com.adri.kids.inventory.infrastructure.adapter.in.mapper.SizeRestMapper;
import com.adri.kids.shared.domain.dtos.PagedResult;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetSizesHandler implements Command.Handler<GetSizesQuery, PagedResult<SizeSummaryResponse>> {

    private final SizeRepositoryPort sizeRepositoryPort;
    private final SizeRestMapper sizeRestMapper;

    @Transactional(readOnly = true, timeout = 10, isolation = Isolation.READ_COMMITTED)
    @Cacheable(
            value = "sizes_page",
            key = "'size_page:' + #query.pageable().pageSize + '-' + #query.pageable().pageNumber + '-' + (#query.searchText ?: 'empty') + '-' + #query.pageable().sort",
            unless = "#result.content.empty"
    )
    @Override
    public PagedResult<SizeSummaryResponse> handle(GetSizesQuery query) {

        var sizePage = sizeRepositoryPort.findAll(query.pageable());

        var mutableContent = sizePage.stream()
                .map(sizeRestMapper::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));

        return new PagedResult<>(
                mutableContent,
                query.pageable().getPageNumber(),
                query.pageable().getPageSize(),
                sizePage.getTotalElements(),
                sizePage.getTotalPages()
        );
    }
}
