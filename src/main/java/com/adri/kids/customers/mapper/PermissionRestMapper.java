package com.adri.kids.customers.mapper;

import com.adri.kids.customers.dto.reponse.PermissionResponse;
import com.adri.kids.customers.dto.request.PermissionRequest;
import com.adri.kids.customers.entity.PermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionRestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PermissionEntity toEntity(PermissionRequest request);

    PermissionResponse toResponse(PermissionEntity entity);

}
