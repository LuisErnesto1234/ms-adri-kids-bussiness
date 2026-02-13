package com.adri.kids.orders.infrastructure.adapter.out.persistence.mapper;

import com.adri.kids.orders.domain.model.OrderItem;

import com.adri.kids.orders.infrastructure.adapter.out.persistence.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemEntityMapper {

    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "variantId", source = "variantId")
    OrderItem toDomain(OrderItemEntity entity);

    OrderItemEntity toEntity(OrderItem domain);

}
