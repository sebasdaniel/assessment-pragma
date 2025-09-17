package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Dish;

public interface IDishServicePort {

    void saveDish(Dish dish);

    Dish updateDish(Dish dish);

    void changeDishStatus(Long dishId, Boolean active, Long userId);
}
