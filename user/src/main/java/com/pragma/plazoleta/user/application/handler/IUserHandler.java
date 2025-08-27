package com.pragma.plazoleta.user.application.handler;

import com.pragma.plazoleta.user.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.user.application.dto.response.UserResponseDto;

import java.util.List;

public interface IUserHandler {

    void saveUserOwner(UserRequestDto userRequestDto);

    List<UserResponseDto> getAllUsers();
}