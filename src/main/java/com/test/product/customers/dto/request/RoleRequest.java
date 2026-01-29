package com.test.product.customers.dto.request;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record RoleRequest(
        String name,
        Set<UUID> permissionsId
) {
}
