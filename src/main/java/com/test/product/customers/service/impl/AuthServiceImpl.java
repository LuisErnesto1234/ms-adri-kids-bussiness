package com.test.product.customers.service.impl;

import com.test.product.customers.dto.reponse.AuthResponse;
import com.test.product.customers.dto.request.LoginRequest;
import com.test.product.customers.dto.request.RefreshTokenRequest;
import com.test.product.customers.security.JwtService;
import com.test.product.customers.security.RefreshTokenService;
import com.test.product.customers.service.AuthService;
import com.test.product.customers.utils.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsService userDetailsService;

    @Override
    public AuthResponse login(LoginRequest request) {

        // 1. La lógica "sucia" de Spring Security vive aquí, no en el controlador
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        // 2. Extraemos el usuario de forma segura
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (userDetails == null) {
            throw new NotFoundException("No se pudo obtener el usuario, del contexto de seguridad");
        }

        // 3. Generar Access Token (JWT)
        String accessToken = jwtService.generateToken(userDetails);

        // 3. Generar Refresh Token (UUID en Redis) <-- NUEVO
        String refreshToken = refreshTokenService.createRefreshToken(request.email());

        return new AuthResponse(accessToken, refreshToken, "Bearer");
    }

    @Override
    public void logout(String authHeader, String refreshToken) { // <--- Agregamos el parámetro

        // 1. Invalidar el JWT (Access Token)
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            jwtService.blacklistToken(jwt);
        }

        // 2. Invalidar el Refresh Token (Borrarlo de Redis)
        if (refreshToken != null) {
            refreshTokenService.deleteRefreshToken(refreshToken);
        }

        throw new NotFoundException("No se pudo realizar el login debido a que no esta presente los datos requeridos");
    }

    @Override
    public AuthResponse generateRefreshToken(RefreshTokenRequest tokenRequest) {

        String refreshToken = tokenRequest.refreshToken();

        return refreshTokenService.findByToken(refreshToken)
                .map(token -> {
                    // 1. El token existe en Redis, buscamos al usuario
                    UserDetails userDetails = userDetailsService.loadUserByUsername(token);

                    // 2. Generamos un NUEVO Access Token (JWT)
                    String newAccessToken = jwtService.generateToken(userDetails);

                    refreshTokenService.deleteRefreshToken(refreshToken);
                    String newRefreshToken = refreshTokenService.createRefreshToken(token);

                    return new AuthResponse(newAccessToken, newRefreshToken, "Bearer");
                })
                .orElseThrow(() -> new RuntimeException("Refresh Token inválido o expirado"));
    }
}
