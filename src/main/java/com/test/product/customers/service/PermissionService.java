package com.test.product.customers.service;

import com.test.product.customers.dto.reponse.PermissionResponse;
import com.test.product.customers.dto.request.PermissionRequest;

public interface PermissionService {
    PermissionResponse createPermission(PermissionRequest request);
}
