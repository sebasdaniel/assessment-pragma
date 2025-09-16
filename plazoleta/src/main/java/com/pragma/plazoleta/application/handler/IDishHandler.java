package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.ChangeDishStatusRequestDto;
import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.UpdateDishRequestDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;

public interface IDishHandler {

    void saveDish(DishRequestDto dishRequestDto);
    DishResponseDto updateDish(UpdateDishRequestDto updateDishRequestDto);
    void changeDishStatus(ChangeDishStatusRequestDto changeDishStatusRequestDto);
}