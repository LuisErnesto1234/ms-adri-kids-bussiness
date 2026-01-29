package com.test.product.customers.service;

import com.test.product.customers.dto.reponse.RoleResponse;
import com.test.product.customers.dto.request.RoleRequest;

public interface RoleService {
    RoleResponse createRole(RoleRequest request);
}
