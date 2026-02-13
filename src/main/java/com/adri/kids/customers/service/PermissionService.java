package com.adri.kids.customers.service;

import com.adri.kids.customers.dto.reponse.PermissionResponse;
import com.adri.kids.customers.dto.request.PermissionRequest;

import java.util.Set;

public interface PermissionService {
    PermissionResponse createPermission(PermissionRequest request);
    Set<PermissionResponse> findAllPermission();
}
