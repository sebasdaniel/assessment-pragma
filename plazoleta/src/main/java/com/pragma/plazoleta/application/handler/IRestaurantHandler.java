package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.response.RestaurantResponseDto;

import java.util.List;

public interface IRestaurantHandler {

    void saveRestaurant(RestaurantRequestDto restaurantRequestDto);

    List<RestaurantResponseDto> getAllObjects();
}