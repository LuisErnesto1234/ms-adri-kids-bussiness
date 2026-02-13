package com.adri.kids.customers.dto.request;

import com.adri.kids.customers.dto.enums.Status;
import lombok.Builder;

@Builder
public record PermissionRequest(String name, Status status) {
}
