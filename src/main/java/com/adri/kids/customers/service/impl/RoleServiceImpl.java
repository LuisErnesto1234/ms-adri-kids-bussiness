package com.adri.kids.customers.service.impl;

import com.adri.kids.customers.dto.reponse.RoleResponse;
import com.adri.kids.customers.dto.request.RoleRequest;
import com.adri.kids.customers.mapper.RoleRestMapper;
import com.adri.kids.customers.repository.RoleJpaRepository;
import com.adri.kids.customers.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleJpaRepository roleJpaRepository;
    private final RoleRestMapper roleRestMapper;

    @Transactional(rollbackFor = RuntimeException.class, timeout = 1000, propagation = Propagation.REQUIRES_NEW)
    @Override
    public RoleResponse createRole(RoleRequest request) {
        return roleRestMapper.toResponse(roleJpaRepository.save(roleRestMapper.toEntity(request)));
    }
}
