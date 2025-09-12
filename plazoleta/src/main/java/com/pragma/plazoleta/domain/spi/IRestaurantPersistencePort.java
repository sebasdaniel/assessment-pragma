package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.Restaurant;
import java.util.List;

public interface IRestaurantPersistencePort {
    Restaurant saveRestaurant(Restaurant restaurant);

    List<Restaurant> getAllObjects();

    boolean exist(Long id);

    boolean matchRestaurantOwner(Long restaurantId, Long ownerId);
}