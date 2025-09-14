package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.exception.ObjectNotFoundException;
import com.pragma.plazoleta.domain.exception.RequiredDataException;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class DishUseCase implements IDishServicePort {

    private static final String PRICE_EXCEPTION = "Price must be a positive number";
    private static final String REQUIRED_DATA_EXCEPTION = "One or more required data is not present";
    private static final String RESTAURANT_NOT_FOUND_EXCEPTION = "The restaurant related with the dish does not exist";
    private static final String RESTAURANT_AND_OWNER_NOT_MATCH_EXCEPTION = "The restaurant doesn't belong to the creator of the dish";

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantServicePort restaurantServicePort;

    @Override
    public void saveDish(Dish dish) {
        if (!haveAllData(dish)) {
            throw new RequiredDataException(REQUIRED_DATA_EXCEPTION);
        }

        if (!haveValidDishPrice(dish.getPrice())) {
            throw new DomainException(PRICE_EXCEPTION);
        }

        if (!restaurantServicePort.exist(dish.getRestaurantId())) {
            throw new ObjectNotFoundException(RESTAURANT_NOT_FOUND_EXCEPTION);
        }

        if (!restaurantServicePort.matchOwner(dish.getRestaurantId(), dish.getCreatorId())) {
            throw new DomainException(RESTAURANT_AND_OWNER_NOT_MATCH_EXCEPTION);
        }

        dish.setActive(true);

        dishPersistencePort.saveDish(dish);
    }

    @Override
    public Dish updateDish(Dish dish) {
        if (!haveAllDataForUpdate(dish)) {
            throw new RequiredDataException(REQUIRED_DATA_EXCEPTION);
        }

        if (!haveValidDishPrice(dish.getPrice())) {
            throw new DomainException(PRICE_EXCEPTION);
        }

        Dish savedDish = dishPersistencePort.getDish(dish.getId());

        if (!restaurantServicePort.matchOwner(savedDish.getRestaurantId(), dish.getCreatorId())) {
            throw new DomainException(RESTAURANT_AND_OWNER_NOT_MATCH_EXCEPTION);
        }

        savedDish.setPrice(dish.getPrice());
        savedDish.setDescription(dish.getDescription());

        return dishPersistencePort.saveDish(savedDish);
    }

    private boolean haveAllData(Dish dish) {
        return !isNull(dish.getName()) && !isNull(dish.getPrice()) && !isNull(dish.getDescription()) &&
                !isNull(dish.getUrlImage()) && !isNull(dish.getCategory()) &&
                !isNull(dish.getRestaurantId()) && !isNull(dish.getCreatorId());
    }

    private boolean haveAllDataForUpdate(Dish dish) {
        return !isNull(dish.getId()) && !isNull(dish.getPrice()) &&
                !isNull(dish.getDescription()) && !isNull(dish.getCreatorId());
    }

    private boolean haveValidDishPrice(Integer price) {
        return !isNull(price) && price > 0;
    }
}
