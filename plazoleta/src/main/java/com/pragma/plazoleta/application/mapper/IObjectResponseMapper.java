package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.response.ObjectResponseDto;
import com.pragma.plazoleta.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IObjectResponseMapper {
    ObjectResponseDto toResponse(Restaurant restaurant);

    List<ObjectResponseDto> toResponseList(List<Restaurant> restaurantList);
}
