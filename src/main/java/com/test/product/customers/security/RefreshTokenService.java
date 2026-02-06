package com.test.product.customers.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final StringRedisTemplate stringRedisTemplate;

    @Value("${application.security.jwt.refresh-token.expiration:7}")
    private long refreshExpiration;

    /**
     * Crea un refresh token opaco (UUID) y lo guarda en Redis
     * Key: "refresh_token:{uuid}" -> Value: "user_email"
     */
    public String createRefreshToken(String email){
        String refreshToken = UUID.randomUUID().toString();
        String key = "rt:" + refreshToken;

        stringRedisTemplate.opsForValue().set(key, email, refreshExpiration, TimeUnit.DAYS);

        return refreshToken;
    }

    /**
     * Valida si el token existe en Redis y devuelve el email asociado.
     * Si no existe (expiró o fue revocado), retorna Empty.
     */
    public Optional<String> findByToken(String token){
        String key = "rt:" + token;
        String email = stringRedisTemplate.opsForValue().get(key);
        return Optional.ofNullable(email);
    }

    /**
     * Elimina el refresh token (para Logout o Rotación)
     */
    public void deleteRefreshToken(String token) {
        String key = "rt:" + token;
        stringRedisTemplate.delete(key);
    }

}
