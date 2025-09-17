package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.ChangeDishStatusRequestDto;
import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.UpdateDishRequestDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;
import com.pragma.plazoleta.application.handler.IDishHandler;
import com.pragma.plazoleta.application.mapper.IDishRequestMapper;
import com.pragma.plazoleta.application.mapper.IDishResponseMapper;
import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.model.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;
    private final IDishResponseMapper dishResponseMapper;

    @Override
    public void saveDish(DishRequestDto dishRequestDto) {
        Dish dish = dishRequestMapper.toDish(dishRequestDto);
        dishServicePort.saveDish(dish);
    }

    @Override
    public DishResponseDto updateDish(UpdateDishRequestDto updateDishRequestDto) {
        var dish = dishRequestMapper.toDish(updateDishRequestDto);
        var updatedDish = dishServicePort.updateDish(dish);
        return dishResponseMapper.toResponse(updatedDish);
    }

    @Override
    public void changeDishStatus(ChangeDishStatusRequestDto changeDishStatusRequestDto) {
        dishServicePort.changeDishStatus(
                changeDishStatusRequestDto.getId(),
                changeDishStatusRequestDto.getActive(),
                changeDishStatusRequestDto.getCallerId()
        );
    }
}