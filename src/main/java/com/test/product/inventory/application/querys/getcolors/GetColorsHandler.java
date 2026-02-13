package com.test.product.inventory.application.querys.getcolors;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.port.out.ColorRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.color.ColorSummaryResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.ColorRestMapper;

import com.test.product.shared.domain.dtos.PagedResult;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetColorsHandler implements Command.Handler<GetColorsQuery, PagedResult<ColorSummaryResponse>> {

    private final ColorRepositoryPort colorRepositoryPort;
    private final ColorRestMapper colorRestMapper;

    @Transactional(readOnly = true)
    @Cacheable(
            value = "color_page",
            key = "'color_page:' + #query.pageable().pageSize + '-' + #query.pageable().pageNumber + '-' + (#query.filterText ?: 'empty')",
            unless = "#result.content.empty"
    )
    @Override
    public PagedResult<ColorSummaryResponse> handle(GetColorsQuery query) {

        var domainPage = colorRepositoryPort.findAll(query.pageable());

        ArrayList<ColorSummaryResponse> mutableColors = domainPage.getContent()
                .stream()
                .map(colorRestMapper::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));

        return new PagedResult<>(
                mutableColors,
                query.pageable().getPageNumber(),
                query.pageable().getPageSize(),
                domainPage.getTotalElements(),
                domainPage.getTotalPages()
        );
    }
}
