package com.test.product.customers.dto.reponse;

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
