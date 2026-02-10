package com.test.product.shared.domain.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ApiResponse<T> implements Serializable {
    private T data;
    private Long httpCode;
    private String message;
    private Instant timeStamp;

    @Serial
    private static final long serialVersionUID = 1L;

    public static <T> ApiResponse<T> buildCreated(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .httpCode(201L)
                .message("Created")
                .timeStamp(Instant.now())
                .build();
    }

    public static <T> ApiResponse<T> buildOk(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .httpCode(200L)
                .message("Ok")
                .timeStamp(Instant.now())
                .build();
    }

    public static <T> ApiResponse<T> buildOk() {
        return ApiResponse.<T>builder()
                .httpCode(200L)
                .message("Ok")
                .timeStamp(Instant.now())
                .build();
    }
}
