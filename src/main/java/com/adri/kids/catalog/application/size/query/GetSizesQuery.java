package com.adri.kids.catalog.application.size.query;

import an.awesome.pipelinr.Command;
import com.adri.kids.catalog.infrastructure.adapter.in.dto.response.SizeSummaryResponse;
import com.adri.kids.shared.domain.dtos.PagedResult;
import org.springframework.data.domain.Pageable;

public record GetSizesQuery(Pageable pageable, String searchText)
        implements Command<PagedResult<SizeSummaryResponse>> {
}
