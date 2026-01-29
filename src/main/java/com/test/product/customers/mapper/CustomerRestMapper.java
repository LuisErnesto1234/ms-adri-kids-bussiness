package com.test.product.customers.mapper;

import com.test.product.customers.dto.reponse.CustomerResponse;
import com.test.product.customers.dto.request.CustomerRequest;
import com.test.product.customers.entity.CustomerEntity;

import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleRestMapper.class})
public interface CustomerRestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    CustomerEntity toEntity(CustomerRequest request);

    @Mapping(target = "roles", source = "roles")
    CustomerResponse toResponse(CustomerEntity customerEntity);

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    void updateCustomerEntity(CustomerRequest request, @MappingTarget CustomerEntity customerEntity);
}
