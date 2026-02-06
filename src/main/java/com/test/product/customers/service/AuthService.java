package com.test.product.customers.service;

import com.test.product.customers.dto.reponse.AuthResponse;
import com.test.product.customers.dto.request.LoginRequest;
import com.test.product.customers.dto.request.RefreshTokenRequest;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);

    void logout(String authHeader, String refreshToken);

    AuthResponse generateRefreshToken(RefreshTokenRequest tokenRequest);
}
