package com.adri.kids.orders.infrastructure.adapter.in.mapper;

import com.adri.kids.orders.domain.model.ShippingRate;
import com.adri.kids.orders.infrastructure.adapter.in.dto.response.ShippingRateResponse;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShippingRateRestMapper {

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    ShippingRateResponse toResponse(ShippingRate shippingRate);
}
