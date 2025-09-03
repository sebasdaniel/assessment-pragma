package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.request.UpdateDishRequestDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;
import com.pragma.plazoleta.application.dto.response.RestaurantResponseDto;

import java.util.List;

public interface IDishHandler {

    void saveDish(DishRequestDto dishRequestDto);
    DishResponseDto updateDish(UpdateDishRequestDto updateDishRequestDto);
}