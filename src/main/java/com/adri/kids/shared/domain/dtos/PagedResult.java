package com.adri.kids.shared.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PagedResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private long totalPages;

    @SuppressWarnings("java:S1319")
    public PagedResult(ArrayList<T> content, int page, int size, long totalElements, long totalPages) {
        this.content = new ArrayList<>(content);
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public PagedResult<T> toPagedResult(List<T> content) {
        return new PagedResult<>(content, page, size, totalElements, totalPages);
    }
}