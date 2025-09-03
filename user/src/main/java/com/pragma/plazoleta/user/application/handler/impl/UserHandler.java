package com.pragma.plazoleta.user.application.handler.impl;

import com.pragma.plazoleta.user.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.user.application.dto.response.UserResponseDto;
import com.pragma.plazoleta.user.application.handler.IUserHandler;
import com.pragma.plazoleta.user.application.mapper.IUserRequestMapper;
import com.pragma.plazoleta.user.application.mapper.IUserResponseMapper;
import com.pragma.plazoleta.user.domain.api.IUserServicePort;
import com.pragma.plazoleta.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;

    @Override
    public void saveUserOwner(UserRequestDto userRequestDto) {
        User user = userRequestMapper.toUser(userRequestDto);
        userServicePort.saveOwner(user);
    }

    @Override
    public UserResponseDto getUser(Long id) {
        User user = userServicePort.getUser(id);
        return userResponseMapper.toResponse(user);
    }
}