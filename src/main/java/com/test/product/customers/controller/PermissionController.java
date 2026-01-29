package com.test.product.customers.controller;

import com.test.product.customers.dto.reponse.ApiResponse;
import com.test.product.customers.dto.reponse.PermissionResponse;
import com.test.product.customers.dto.request.PermissionRequest;
import com.test.product.customers.service.PermissionService;
import com.test.product.customers.utils.ConstantUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.time.Instant;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<PermissionResponse>> createCustomer(@Valid @RequestBody PermissionRequest request) {

        PermissionResponse response = permissionService.createPermission(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<PermissionResponse>builder()
                .data(response)
                .code(ConstantUtil.CREATED_CODE_HTTP)
                .message(ConstantUtil.CREATED_MESSAGE)
                .timeStamp(Instant.now())
                .build());
    }

}
