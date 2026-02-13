package com.adri.kids.customers.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    private final StringRedisTemplate stringRedisTemplate;

    // Método para obtener la llave criptográfica correctamente
    private SecretKey getSignInKey() {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] keyBytes = decoder.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                // Guardamos solo los roles/permisos como Strings, no el objeto completo
                .claim("authorities", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 Horas
                .signWith(getSignInKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Método genérico para extraer cualquier claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey()) // 1. Especifica la clave de verificación
                .build()                    // 2. Construye el parser
                .parseSignedClaims(token)   // 3. Analiza el token
                .getPayload();              // 4. Obtiene el cuerpo (claims)
    }

    // Validar token contra el usuario y expiración
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // Ahora validamos: Usuario coincide AND Token no expirado AND Token no está en blacklist
        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && !isTokenBlacklisted(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public void blacklistToken(String token) {
        Date expirationTime = extractExpiration(token);
        long timeToLive = expirationTime.getTime() - System.currentTimeMillis();

        if (timeToLive > 0) {
            // CORRECCIÓN: Usamos 'set' para GUARDAR y EXPIRAR en un solo paso atómico.
            // Usamos MILLISECONDS porque 'timeToLive' viene de una resta de Dates (ms).
            stringRedisTemplate.opsForValue()
                    .set(token, "blacklisted", timeToLive, TimeUnit.MILLISECONDS);
        }
    }

    public boolean isTokenBlacklisted(String token) {
        // Si existe en Redis, es inválido
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(token));
    }
}