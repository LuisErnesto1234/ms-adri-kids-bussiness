package com.adri.kids.customers.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CustomerRequest(
        @NotNull(message = "El primer nombre no debe ser un valor nulo!")
        @NotBlank(message = "Los nombres no deben estar en blanco.")
        String firstName, String lastName, String email, String password, String phone) {
}
