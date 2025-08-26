package com.pragma.plazoleta.user.application.handler.impl;

import com.pragma.plazoleta.user.application.dto.request.ObjectRequestDto;
import com.pragma.plazoleta.user.application.dto.response.ObjectResponseDto;
import com.pragma.plazoleta.user.application.handler.IObjectHandler;
import com.pragma.plazoleta.user.application.mapper.IObjectRequestMapper;
import com.pragma.plazoleta.user.application.mapper.IObjectResponseMapper;
import com.pragma.plazoleta.user.domain.api.IUserServicePort;
import com.pragma.plazoleta.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ObjectHandler implements IObjectHandler {

    private final IUserServicePort objectServicePort;
    private final IObjectRequestMapper objectRequestMapper;
    private final IObjectResponseMapper objectResponseMapper;

    @Override
    public void saveObject(ObjectRequestDto objectRequestDto) {
        User user = objectRequestMapper.toObject(objectRequestDto);
        objectServicePort.saveOwner(user);
    }

    @Override
    public List<ObjectResponseDto> getAllObjects() {
        return objectResponseMapper.toResponseList(objectServicePort.getAllUsers());
    }
}