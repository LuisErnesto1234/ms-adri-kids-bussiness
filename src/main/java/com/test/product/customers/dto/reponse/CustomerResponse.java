package com.test.product.customers.dto.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.product.customers.dto.enums.Status;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record CustomerResponse(UUID id, String firstName, String lastName,
                               String email, String phone, Set<RoleResponse> roles, Instant createdAt,
                               Instant updatedAt, Status status) {
}
