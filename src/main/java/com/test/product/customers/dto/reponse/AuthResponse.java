package com.test.product.customers.dto.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken, // Nuevo campo
        @JsonProperty("token_type") String tokenType
) {}
