package com.adri.kids.shared.domain.mapper;

import com.adri.kids.shared.domain.dtos.PagedResult;

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