package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantServicePort {

    void saveRestaurant(Restaurant restaurant);

    boolean exist(Long id);

    boolean matchOwner(Long restaurantId, Long creatorId);

    List<Restaurant> getAllObjects();
}