package com.pragma.plazoleta.user.infrastructure.out.jpa.mapper;

import com.pragma.plazoleta.user.domain.model.User;
import com.pragma.plazoleta.user.infrastructure.out.jpa.entity.ObjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IObjectEntityMapper {

    ObjectEntity toEntity(User user);
    User toObjectModel(ObjectEntity objectEntity);
    List<User> toObjectModelList(List<ObjectEntity> userEntityList);
}