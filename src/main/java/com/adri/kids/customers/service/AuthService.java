package com.adri.kids.customers.service;

import com.adri.kids.customers.dto.reponse.AuthResponse;
import com.adri.kids.customers.dto.request.LoginRequest;
import com.adri.kids.customers.dto.request.RefreshTokenRequest;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);

    void logout(String authHeader, String refreshToken);

    AuthResponse generateRefreshToken(RefreshTokenRequest tokenRequest);
}
