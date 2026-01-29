package com.test.product.inventory.infrastructure.adapter.in.dto.request;

import com.test.product.inventory.domain.enums.Status;
import com.test.product.inventory.domain.enums.TypeProduct;

public record SizeRequest(String name, TypeProduct type, String sortOrder, Status status) {
}
