package com.adri.kids.inventory.application.querys.getcategories;

import an.awesome.pipelinr.Command;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.response.category.CategoryCardResponse;
import com.adri.kids.shared.domain.dtos.PagedResult;
import org.springframework.data.domain.Pageable;

public record GetCategoriesQuery(Pageable pageable, String filterText)
        implements Command<PagedResult<CategoryCardResponse>> {
}
