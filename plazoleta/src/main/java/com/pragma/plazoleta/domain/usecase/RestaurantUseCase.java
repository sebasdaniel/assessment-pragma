package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.exception.DataFormatException;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.exception.RequiredDataException;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta.domain.spi.IUserServicePort;

import java.util.List;
import java.util.regex.Pattern;

public class RestaurantUseCase implements IRestaurantServicePort {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{1,12}$");

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
            throw new RequiredDataException("One or more required data not present");
        }

        if (!isValidPhoneNumber(restaurant.getPhoneNumber())) {
            throw new DataFormatException("Phone number is not valid");
        }

        if (!isValidRestaurantName(restaurant.getName())) {
            throw new DataFormatException("Name could not be only numbers");
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

    private boolean isValidRestaurantName(String name){
        try {
            Integer.parseInt(name);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }
}