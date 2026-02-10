package com.test.product.inventory.application.querys.getcolors;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.ColorSummaryResponse;
import com.test.product.shared.domain.dtos.PagedResult;
import org.springframework.data.domain.Pageable;

public record GetColorsQuery(Pageable pageable, String filterText)
        implements Command<PagedResult<ColorSummaryResponse>> {
}
