package com.test.product.inventory.domain.port.in.command.size;

import com.test.product.inventory.domain.enums.Status;
import com.test.product.inventory.domain.enums.TypeProduct;

public record CreateSizeCommand(String name, TypeProduct type, String sortOrder, Status status) {
}
