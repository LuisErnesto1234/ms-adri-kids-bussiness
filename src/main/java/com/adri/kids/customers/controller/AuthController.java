package com.adri.kids.customers.controller;

import com.adri.kids.customers.dto.reponse.ApiResponse;
import com.adri.kids.customers.dto.reponse.AuthResponse;
import com.adri.kids.customers.dto.request.LoginRequest;
import com.adri.kids.customers.dto.request.RefreshTokenRequest;
import com.adri.kids.customers.service.AuthService;
import com.adri.kids.customers.utils.ConstantUtil;

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
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authHeader,
                                                    @RequestBody RefreshTokenRequest refreshTokenRequest) {

        authService.logout(authHeader, refreshTokenRequest.refreshToken());

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(ConstantUtil.OK_CODE_HTTP)
                .message("Sesión cerrada exitosamente") // O "Logout successful"
                .timeStamp(Instant.now())
                .build());
    }

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        var response = authService.generateRefreshToken(refreshTokenRequest);

        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .data(response)
                .code(ConstantUtil.OK_CODE_HTTP)
                .message("Refresh token generado exitosamente")
                .timeStamp(Instant.now())
                .build());
    }

}
