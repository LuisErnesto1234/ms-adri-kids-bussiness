package com.adri.kids.catalog.application.category.query;

import an.awesome.pipelinr.Command;

import com.adri.kids.catalog.domain.model.Category;
import com.adri.kids.catalog.infrastructure.adapter.in.dto.request.filter.CategoryFilterRequest;
import com.adri.kids.shared.domain.dtos.PagedResult;
import com.adri.kids.shared.utils.ConstantUtil;

public record GetCategoriesQuery(int page, int size, CategoryFilterRequest filterRequest)
        implements Command<PagedResult<Category>> {

    public GetCategoriesQuery {
        if (page < ConstantUtil.MIN_PAGE) {
            throw new IllegalArgumentException("El numero de pagina no debe ser menor a, " + ConstantUtil.MIN_PAGE);
        }
        if (size > ConstantUtil.MAX_SIZE) {
            throw new IllegalArgumentException("La cantidad de elementos de la pagina no debe ser mayor a, " + ConstantUtil.MAX_SIZE);
        }
        if (size < ConstantUtil.MIN_SIZE) {
            throw new IllegalArgumentException("La cantidad de elementos de la pagina no debe ser menor a, " + ConstantUtil.MIN_SIZE);
        }
    }

}
