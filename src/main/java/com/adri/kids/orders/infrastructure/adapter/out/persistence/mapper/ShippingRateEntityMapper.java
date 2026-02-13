package com.adri.kids.orders.infrastructure.adapter.out.persistence.mapper;

import com.adri.kids.orders.domain.model.ShippingRate;
import com.adri.kids.orders.infrastructure.adapter.out.persistence.entity.ShippingRateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShippingRateEntityMapper {

    ShippingRate toDomain(ShippingRateEntity entity);

    ShippingRateEntity toEntity(ShippingRate domain);

}
