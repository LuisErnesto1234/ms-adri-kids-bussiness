package com.test.product.customers.dto.request;

import com.test.product.customers.dto.enums.Status;
import lombok.Builder;

@Builder
public record PermissionRequest(String name, Status status) {
}
