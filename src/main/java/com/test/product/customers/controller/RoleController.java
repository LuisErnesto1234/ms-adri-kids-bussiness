package com.test.product.customers.controller;

import com.test.product.customers.dto.reponse.ApiResponse;
import com.test.product.customers.dto.reponse.RoleResponse;
import com.test.product.customers.dto.request.RoleRequest;
import com.test.product.customers.service.RoleService;
import com.test.product.customers.utils.ConstantUtil;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<RoleResponse>> createCustomer(@Valid @RequestBody RoleRequest request) {

        RoleResponse response = roleService.createRole(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<RoleResponse>builder()
                .data(response)
                .code(ConstantUtil.CREATED_CODE_HTTP)
                .message(ConstantUtil.CREATED_MESSAGE)
                .timeStamp(Instant.now())
                .build());
    }

}
