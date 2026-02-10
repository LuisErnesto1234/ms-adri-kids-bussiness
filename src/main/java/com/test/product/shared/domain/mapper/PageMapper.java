package com.test.product.shared.domain.mapper;

import com.test.product.shared.domain.dtos.PagedResult;

import org.springframework.data.domain.Page;

public class PageMapper {

    public static <T> PagedResult<T> fromPage(Page<T> page) {
        return new PagedResult<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public PageMapper() {
    }
}