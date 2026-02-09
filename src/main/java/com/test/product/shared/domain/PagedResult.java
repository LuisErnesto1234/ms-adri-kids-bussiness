package com.test.product.shared.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public record PagedResult<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
