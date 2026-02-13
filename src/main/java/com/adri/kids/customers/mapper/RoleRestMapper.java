package com.adri.kids.customers.mapper;

import com.adri.kids.customers.dto.reponse.RoleResponse;
import com.adri.kids.customers.dto.request.RoleRequest;
import com.adri.kids.customers.entity.RoleEntity;
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
