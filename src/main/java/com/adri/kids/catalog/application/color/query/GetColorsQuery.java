package com.adri.kids.catalog.application.color.query;

import an.awesome.pipelinr.Command;
import com.adri.kids.catalog.infrastructure.adapter.in.dto.response.ColorSummaryResponse;
import com.adri.kids.shared.domain.dtos.PagedResult;
import org.springframework.data.domain.Pageable;

public record GetColorsQuery(Pageable pageable, String filterText)
        implements Command<PagedResult<ColorSummaryResponse>> {
}
