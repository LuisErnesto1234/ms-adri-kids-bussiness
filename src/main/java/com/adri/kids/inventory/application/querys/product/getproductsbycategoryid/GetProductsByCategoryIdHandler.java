package com.adri.kids.inventory.application.querys.product.getproductsbycategoryid;

import an.awesome.pipelinr.Command;

import com.adri.kids.inventory.domain.model.Product;
import com.adri.kids.inventory.domain.port.out.ProductRepositoryPort;
import com.adri.kids.shared.domain.dtos.PagedResult;

import com.adri.kids.shared.domain.mapper.PageMapper;
import com.adri.kids.shared.exceptions.NotFoundException;
import com.adri.kids.shared.utils.ConstantUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetProductsByCategoryIdHandler implements Command.Handler<GetProductsByCategoryIdQuery, PagedResult<Product>> {

    private final ProductRepositoryPort productRepositoryPort;

    @Transactional(readOnly = true, timeout = ConstantUtil.TIME_OUT_TRANSACTION)
    @Override
    public PagedResult<Product> handle(GetProductsByCategoryIdQuery command) {
        var pageable = Pageable.ofSize(command.size()).withPage(command.page());
        var productsFoundPage = productRepositoryPort.findAllByCategoryIdPage(pageable, command.categoryId());

        if (!productsFoundPage.hasContent()) {
            throw new NotFoundException("La categoria, no tiene aun productos asignados");
        }

        return PageMapper.fromPage(productsFoundPage);
    }
}
