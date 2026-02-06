package com.test.product.orders.infrastructure.adapter.in.mapper;

import com.test.product.orders.domain.model.ShippingRate;
import com.test.product.orders.infrastructure.adapter.in.dto.response.ShippingRateResponse;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShippingRateRestMapper {

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    ShippingRateResponse toResponse(ShippingRate shippingRate);
}
