package com.adri.kids.customers.dto.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.adri.kids.customers.dto.enums.Status;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record PermissionResponse(UUID id, String name, Instant createdAt,
                                 Instant updatedAt, Status status) {
}
