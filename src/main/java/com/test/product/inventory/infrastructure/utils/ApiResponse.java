package com.test.product.inventory.infrastructure.utils;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ApiResponse <T>{

    private T data;
    private String message;
    private String code;
    private Instant timeStamp;
}
