package com.adri.kids.customers.service;

import com.adri.kids.customers.dto.reponse.RoleResponse;
import com.adri.kids.customers.dto.request.RoleRequest;

public interface RoleService {
    RoleResponse createRole(RoleRequest request);
}
