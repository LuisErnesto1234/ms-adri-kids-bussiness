package com.adri.kids.customers.dto.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.adri.kids.customers.dto.enums.Status;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record RoleResponse(Long id, String name,
                           Set<PermissionResponse> permissionResponses,
                           Instant createdAt, Instant updatedAt, Status status
) {
}
