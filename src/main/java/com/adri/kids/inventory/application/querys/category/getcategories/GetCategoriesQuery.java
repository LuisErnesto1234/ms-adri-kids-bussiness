package com.adri.kids.inventory.application.querys.category.getcategories;

import an.awesome.pipelinr.Command;

import com.adri.kids.inventory.domain.model.Category;
import com.adri.kids.inventory.infrastructure.adapter.in.dto.request.category.filter.CategoryFilterRequest;
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
