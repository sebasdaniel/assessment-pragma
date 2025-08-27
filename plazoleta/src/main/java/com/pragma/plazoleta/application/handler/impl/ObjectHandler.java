package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.ObjectRequestDto;
import com.pragma.plazoleta.application.dto.response.ObjectResponseDto;
import com.pragma.plazoleta.application.handler.IObjectHandler;
import com.pragma.plazoleta.application.mapper.IObjectRequestMapper;
import com.pragma.plazoleta.application.mapper.IObjectResponseMapper;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ObjectHandler implements IObjectHandler {

    private final IRestaurantServicePort objectServicePort;
    private final IObjectRequestMapper objectRequestMapper;
    private final IObjectResponseMapper objectResponseMapper;

    @Override
    public void saveObject(ObjectRequestDto objectRequestDto) {
        Restaurant restaurant = objectRequestMapper.toObject(objectRequestDto);
        objectServicePort.saveRestaurant(restaurant);
    }

    @Override
    public List<ObjectResponseDto> getAllObjects() {
        return objectResponseMapper.toResponseList(objectServicePort.getAllObjects());
    }
}