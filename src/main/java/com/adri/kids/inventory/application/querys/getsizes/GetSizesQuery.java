package com.adri.kids.inventory.application.querys.getsizes;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.size.SizeSummaryResponse;
import com.adri.kids.shared.domain.dtos.PagedResult;
import org.springframework.data.domain.Pageable;

public record GetSizesQuery(Pageable pageable, String searchText)
        implements Command<PagedResult<SizeSummaryResponse>> {
}
