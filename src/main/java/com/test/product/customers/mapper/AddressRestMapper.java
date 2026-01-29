package com.test.product.customers.mapper;

import com.test.product.customers.dto.reponse.AddressResponse;
import com.test.product.customers.dto.request.AddressRequest;
import com.test.product.customers.entity.AddressEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CustomerRestMapper.class})
public interface AddressRestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    AddressEntity toEntity(AddressRequest request);

    @Mapping(target = "customerResponse", source = "customer")
    AddressResponse toResponse(AddressEntity entity);

}
