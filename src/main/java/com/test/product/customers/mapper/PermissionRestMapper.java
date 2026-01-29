package com.test.product.customers.mapper;

import com.test.product.customers.dto.reponse.PermissionResponse;
import com.test.product.customers.dto.request.PermissionRequest;
import com.test.product.customers.entity.PermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionRestMapper {

    @Mapping(target = "id", ignore = true)
    PermissionEntity toEntity(PermissionRequest request);

    PermissionResponse toResponse(PermissionEntity entity);

}
