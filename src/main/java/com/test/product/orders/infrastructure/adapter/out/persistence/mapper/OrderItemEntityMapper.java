package com.test.product.orders.infrastructure.adapter.out.persistence.mapper;

import com.test.product.orders.domain.model.OrderItem;

import com.test.product.orders.infrastructure.adapter.out.persistence.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemEntityMapper {

    OrderItem toDomain(OrderItemEntity entity);

    OrderItemEntity toEntity(OrderItem domain);

}
