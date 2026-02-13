package com.adri.kids.customers.mapper;

import com.adri.kids.customers.dto.reponse.AddressResponse;
import com.adri.kids.customers.dto.request.AddressRequest;
import com.adri.kids.customers.entity.AddressEntity;

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
