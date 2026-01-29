package com.test.product.customers.controller;

import com.test.product.customers.dto.reponse.AddressResponse;
import com.test.product.customers.dto.reponse.ApiResponse;
import com.test.product.customers.dto.request.AddressRequest;
import com.test.product.customers.service.AddressService;
import com.test.product.customers.utils.ConstantUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping(value = "/api/v1/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<AddressResponse>> createCustomer(@Valid @RequestBody AddressRequest request) {

        AddressResponse response = addressService.createAddress(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<AddressResponse>builder()
                .data(response)
                .code(ConstantUtil.CREATED_CODE_HTTP)
                .message(ConstantUtil.CREATED_MESSAGE)
                .timeStamp(Instant.now())
                .build());
    }

}
