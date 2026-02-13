package com.test.product.inventory.application.querys.getcategories;

import an.awesome.pipelinr.Command;
import com.test.product.inventory.infrastructure.adapter.in.dto.response.category.CategoryCardResponse;
import com.test.product.shared.domain.dtos.PagedResult;
import org.springframework.data.domain.Pageable;

public record GetCategoriesQuery(Pageable pageable, String filterText)
        implements Command<PagedResult<CategoryCardResponse>> {
}
