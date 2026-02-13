package com.adri.kids.customers.dto.request;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record RoleRequest(
        String name,
        Set<UUID> permissionsId
) {
}
