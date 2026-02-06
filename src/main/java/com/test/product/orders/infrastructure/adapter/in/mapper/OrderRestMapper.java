package com.test.product.orders.infrastructure.adapter.in.mapper;

import com.test.product.orders.domain.model.Order;
import com.test.product.orders.infrastructure.adapter.in.dto.response.OrderResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderRestMapper {

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    OrderResponse toResponse(Order domain);

}
