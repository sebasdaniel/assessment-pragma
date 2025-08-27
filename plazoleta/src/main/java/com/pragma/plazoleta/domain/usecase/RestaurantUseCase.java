package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta.domain.spi.IUserServicePort;

import java.util.List;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserServicePort userServicePort;

    public RestaurantUseCase(
            IRestaurantPersistencePort restaurantPersistencePort,
            IUserServicePort userServicePort
    ) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userServicePort = userServicePort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        if (!haveRestaurantValidData(restaurant)) {
            throw new DomainException("One or more required data not present");
        }

        if (!restaurant.isValidPhoneNumber()) {
            throw new DomainException("Phone number is not valid");
        }

        if (!restaurant.isValidName()) {
            throw new DomainException("Name could not be only numbers");
        }

        if (!userServicePort.userExists(restaurant.getOwnerId())) {
            throw new DomainException("The owner does not exist");
        }

        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public List<Restaurant> getAllObjects() {
        return restaurantPersistencePort.getAllObjects();
    }

    private boolean haveRestaurantValidData(Restaurant restaurant) {
        return restaurant.getNit() != null && restaurant.getName() != null && restaurant.getAddress() != null
                && restaurant.getPhoneNumber() != null && restaurant.getUrlLogo() != null
                && restaurant.getOwnerId() != null;
    }
}