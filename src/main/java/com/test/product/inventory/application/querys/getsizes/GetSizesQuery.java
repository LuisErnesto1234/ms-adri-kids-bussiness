package com.test.product.inventory.application.querys.getsizes;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.SizeSummaryResponse;
import com.test.product.shared.domain.dtos.PagedResult;
import org.springframework.data.domain.Pageable;

public record GetSizesQuery(Pageable pageable, String searchText)
        implements Command<PagedResult<SizeSummaryResponse>> {
}
