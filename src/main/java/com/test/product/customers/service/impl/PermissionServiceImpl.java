package com.test.product.customers.service.impl;

import com.test.product.customers.dto.reponse.PermissionResponse;
import com.test.product.customers.dto.request.PermissionRequest;
import com.test.product.customers.mapper.PermissionRestMapper;
import com.test.product.customers.repository.PermissionJpaRepository;
import com.test.product.customers.service.PermissionService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionJpaRepository permissionJpaRepository;
    private final PermissionRestMapper permissionRestMapper;

    @Transactional(rollbackFor = RuntimeException.class, timeout = 1000, propagation = Propagation.REQUIRES_NEW)
    @Override
    public PermissionResponse createPermission(PermissionRequest request) {
        return permissionRestMapper.toResponse(permissionJpaRepository.save(permissionRestMapper.toEntity(request)));
    }
}
