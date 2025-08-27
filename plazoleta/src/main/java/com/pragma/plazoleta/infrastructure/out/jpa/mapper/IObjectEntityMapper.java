package com.pragma.plazoleta.infrastructure.out.jpa.mapper;

import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.ObjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IObjectEntityMapper {

    ObjectEntity toEntity(Restaurant user);
    Restaurant toObjectModel(ObjectEntity objectEntity);
    List<Restaurant> toObjectModelList(List<ObjectEntity> userEntityList);
}