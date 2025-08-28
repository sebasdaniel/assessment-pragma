package com.pragma.plazoleta.user.application.mapper;

import com.pragma.plazoleta.user.application.dto.response.UserResponseDto;
import com.pragma.plazoleta.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserResponseMapper {
    UserResponseDto toResponse(User user);

}
