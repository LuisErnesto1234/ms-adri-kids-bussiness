package com.test.product.inventory.application.querys.getcolors;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.domain.port.out.ColorRepositoryPort;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ColorSummaryResponse;
import com.test.product.inventory.infrastructure.adapter.in.mapper.ColorRestMapper;
import com.test.product.shared.domain.PageMapper;
import com.test.product.shared.domain.PagedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetColorsHandler implements Command.Handler<GetColorsQuery, PagedResult<ColorSummaryResponse>> {

    private final ColorRepositoryPort colorRepositoryPort;
    private final ColorRestMapper colorRestMapper;

    @Transactional(readOnly = true)
    @Cacheable(
            value = "colors_page",
            key = "#query.pageable().pageNumber + '-' + #query.pageable().pageSize + '-' + #query.filterText()",
            unless = "#result.content().empty"
    )
    @Override
    public PagedResult<ColorSummaryResponse> handle(GetColorsQuery query) {

        var result = colorRepositoryPort.findAll(query.pageable());

        var response = result.map(colorRestMapper::toResponse);

        return PageMapper.fromPage(response);
    }
}
