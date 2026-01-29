package com.test.product.customers.controller;

import com.test.product.customers.dto.reponse.ApiResponse;
import com.test.product.customers.dto.reponse.AuthResponse;
import com.test.product.customers.dto.request.LoginRequest;
import com.test.product.customers.service.AuthService;
import com.test.product.customers.utils.ConstantUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest loginRequest) {

        AuthResponse authResponse = authService.login(loginRequest);

        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .data(authResponse)
                .code(ConstantUtil.OK_CODE_HTTP)
                .message(ConstantUtil.LOGIN_SUCCESSFULLY)
                .timeStamp(Instant.now())
                .build());
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authHeader) {

        authService.logout(authHeader);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(ConstantUtil.OK_CODE_HTTP)
                .message("Sesión cerrada exitosamente") // O "Logout successful"
                .timeStamp(Instant.now())
                .build());
    }
}
