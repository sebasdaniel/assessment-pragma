package com.pragma.plazoleta.user.application.handler;

import com.pragma.plazoleta.user.application.dto.request.ObjectRequestDto;
import com.pragma.plazoleta.user.application.dto.response.ObjectResponseDto;

import java.util.List;

public interface IObjectHandler {

    void saveObject(ObjectRequestDto objectRequestDto);

    List<ObjectResponseDto> getAllObjects();
}