package com.test.product.customers.service.impl;

import com.test.product.customers.dto.reponse.AuthResponse;
import com.test.product.customers.dto.request.LoginRequest;
import com.test.product.customers.security.JwtService;
import com.test.product.customers.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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
            throw new RuntimeException("No se pudo obtener el usuario, del contexto de seguridad");
        }

        // 3. Generamos el token
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }

    @Override
    public void logout(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);

        jwtService.blacklistToken(token);
        // Aquí podrías agregar lógica adicional (ej: auditoría de "Usuario X salió")
    }
}
