package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.exception.ObjectNotFoundException;
import com.pragma.plazoleta.domain.exception.RequiredDataException;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantServicePort restaurantServicePort;

    @Override
    public void saveDish(Dish dish) {
        if (!haveRequiredData(dish)) {
            throw new RequiredDataException("One or more required data for dish is not present");
        }

        if (!restaurantServicePort.exist(dish.getRestaurantId())) {
            throw new ObjectNotFoundException("The restaurant related with the dish does not exist");
        }

        dish.setActive(true);

        dishPersistencePort.saveDish(dish);
    }

    @Override
    public Dish updateDish(Dish dish) {

        if (dish.getId() == null || dish.getPrice() == null || dish.getDescription() == null) {
            throw new RequiredDataException("Required data for update dish is not present");
        }

        var savedDish = dishPersistencePort.getDish(dish.getId());
        savedDish.setPrice(dish.getPrice());
        savedDish.setDescription(dish.getDescription());

        return dishPersistencePort.saveDish(savedDish);
    }

    private boolean haveRequiredData(Dish dish) {
        return dish.getName() != null && dish.getPrice() != null && dish.getDescription() != null &&
                dish.getUrlImage() != null && dish.getCategory() != null && dish.getRestaurantId() != null;
    }
}
