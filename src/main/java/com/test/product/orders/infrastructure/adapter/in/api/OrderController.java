package com.test.product.orders.infrastructure.adapter.in.api;

import an.awesome.pipelinr.Pipeline;

import com.test.product.orders.app.usecases.additem.AddItemCommand;
import com.test.product.orders.app.usecases.applycoupon.ApplyCouponCommand;
import com.test.product.orders.app.usecases.cancelorder.CancelOrderCommand;
import com.test.product.orders.app.usecases.createorder.CreateOrderCommand;
import com.test.product.orders.app.usecases.markshippedorder.MarkShippedOrderCommand;
import com.test.product.orders.infrastructure.adapter.in.dto.request.AddItemRequest;
import com.test.product.orders.infrastructure.adapter.in.dto.request.ApplyCouponRequest;
import com.test.product.orders.infrastructure.adapter.in.dto.request.MarkShippedOrderRequest;
import com.test.product.orders.infrastructure.adapter.in.dto.request.SetShippingInfoRequest;
import com.test.product.orders.infrastructure.adapter.in.dto.response.OrderResponse;
import com.test.product.orders.infrastructure.adapter.in.mapper.OrderRestMapper;
import com.test.product.shared.domain.dtos.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final Pipeline pipeline;
    private final OrderRestMapper orderRestMapper;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<UUID>> createOrder(@AuthenticationPrincipal UserDetails userDetails) {

        var command = new CreateOrderCommand(userDetails.getUsername());

        var response = command.execute(pipeline);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UUID>builder()
                        .data(response)
                        .build());
    }

    @PostMapping(value = "/add-item/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> addItem(@AuthenticationPrincipal UserDetails userDetails,
                                                              @PathVariable UUID orderId,
                                                              @Valid @RequestBody AddItemRequest request) {

        var command = new AddItemCommand(orderId, request.variantId(), request.quantity(), userDetails.getUsername());

        var responseDomain = command.execute(pipeline);

        var orderResponse = orderRestMapper.toResponse(responseDomain);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<OrderResponse>builder()
                .data(orderResponse)
                .message("OK")
                .httpCode(200L)
                .timeStamp(Instant.now())
                .build());
    }

    @PostMapping(value = "/apply-coupon/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> applyCoupon(@PathVariable UUID orderId,
                                                                  @Valid @RequestBody ApplyCouponRequest request) {
        var command = new ApplyCouponCommand(orderId, request.couponCode());

        var responseDomain = command.execute(pipeline);

        var responseRest = orderRestMapper.toResponse(responseDomain);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<OrderResponse>builder()
                .data(responseRest)
                .message("OK")
                .httpCode(200L)
                .timeStamp(Instant.now())
                .build());
    }

    @PostMapping(value = "/cancel-order/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(@PathVariable UUID orderId) {
        var command = new CancelOrderCommand(orderId);
        var responseDomain = command.execute(pipeline);
        var responseRest = orderRestMapper.toResponse(responseDomain);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<OrderResponse>builder()
                .data(responseRest)
                .message("OK")
                .httpCode(200L)
                .timeStamp(Instant.now())
                .build());
    }

    @PostMapping(value = "/mark-as-shipped/{orderId}/")
    public ResponseEntity<ApiResponse<OrderResponse>> markAsShipped(@PathVariable UUID orderId,
                                                                    @RequestBody MarkShippedOrderRequest request) {

        var command = new MarkShippedOrderCommand(orderId, request.carrier());

        var domainResponse = command.execute(pipeline);

        var responseRest = orderRestMapper.toResponse(domainResponse);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<OrderResponse>builder()
                .data(responseRest)
                .message("OK")
                .httpCode(200L)
                .timeStamp(Instant.now())
                .build());

    }

    @PutMapping(value = "/edit-info/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> editShippingInfo(
            @PathVariable UUID orderId,
            @RequestBody SetShippingInfoRequest request) {

        var command = request.toCommand(orderId);

        var responseDomain = command.execute(pipeline);

        var responseRest = orderRestMapper.toResponse(responseDomain);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<OrderResponse>builder()
                .data(responseRest)
                .message("OK")
                .httpCode(200L)
                .timeStamp(Instant.now())
                .build());
    }
}

