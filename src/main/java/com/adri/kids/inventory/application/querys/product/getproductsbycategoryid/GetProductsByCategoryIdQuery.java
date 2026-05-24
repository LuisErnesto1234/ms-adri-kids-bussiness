package com.adri.kids.inventory.application.querys.product.getproductsbycategoryid;

import an.awesome.pipelinr.Command;

import com.adri.kids.inventory.domain.model.Product;
import com.adri.kids.shared.domain.dtos.PagedResult;
import com.adri.kids.shared.utils.ConstantUtil;

import java.util.UUID;

public record GetProductsByCategoryIdQuery(UUID categoryId,
                                           int page,
                                           int size) implements Command<PagedResult<Product>> {
    public GetProductsByCategoryIdQuery {
        if (categoryId == null) {
            throw new IllegalArgumentException("El id de la categoria no puede ser nulo.");
        }
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
