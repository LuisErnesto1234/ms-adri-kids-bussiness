package com.test.product.customers.service;

import com.test.product.customers.dto.reponse.PermissionResponse;
import com.test.product.customers.dto.request.PermissionRequest;

import java.util.List;
import java.util.Set;

public interface PermissionService {
    PermissionResponse createPermission(PermissionRequest request);
    Set<PermissionResponse> findAllPermission();
}
