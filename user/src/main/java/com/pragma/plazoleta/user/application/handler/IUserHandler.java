package com.pragma.plazoleta.user.application.handler;

import com.pragma.plazoleta.user.application.dto.request.UserRequestDto;

public interface IUserHandler {

    void saveUserOwner(UserRequestDto userRequestDto);
}