package com.adri.kids.orders.infrastructure.adapter.out.persistence.mapper;

import com.adri.kids.orders.domain.model.Order;
import com.adri.kids.orders.infrastructure.adapter.out.persistence.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderEntityMapper {

    Order toDomain(OrderEntity entity);

    OrderEntity toEntity(Order order);

}
