package com.test.product.customers.mapper;

import com.test.product.customers.dto.reponse.RoleResponse;
import com.test.product.customers.dto.request.RoleRequest;
import com.test.product.customers.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {PermissionRestMapper.class})
public interface RoleRestMapper {

    @Mapping(target = "id", ignore = true)
    RoleEntity toEntity(RoleRequest request);

    @Mapping(target = "permissionResponses", source = "permissions")
    RoleResponse toResponse(RoleEntity roleEntity);

}
