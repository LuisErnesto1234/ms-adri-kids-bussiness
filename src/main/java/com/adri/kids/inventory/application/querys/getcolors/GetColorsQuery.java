package com.adri.kids.inventory.application.querys.getcolors;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.color.ColorSummaryResponse;
import com.adri.kids.shared.domain.dtos.PagedResult;
import org.springframework.data.domain.Pageable;

public record GetColorsQuery(Pageable pageable, String filterText)
        implements Command<PagedResult<ColorSummaryResponse>> {
}
