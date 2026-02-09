package com.test.product.shared.domain;

import lombok.Builder;

import java.time.Instant;

@Builder
public class ApiResponse<T> {
    private T data;
    private Long httpCode;
    private String message;
    private Instant timeStamp;

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
