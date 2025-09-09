package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.exception.DataFormatException;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.exception.RequiredDataException;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta.domain.spi.IUserServicePort;
import java.util.List;
import java.util.regex.Pattern;

public class RestaurantUseCase implements IRestaurantServicePort {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{1,12}$");
    private static final String REQUIRED_DATA_EXCEPTION = "Missing one or more required data";
    private static final String WRONG_PHONE_NUMBER_EXCEPTION = "Phone number is not valid";
    private static final String ONLY_NUMBER_NAME_EXCEPTION = "Name could not be only numbers";
    private static final String OWNER_NOT_FOUND_EXCEPTION = "The owner does not exist";
    private static final String WRONG_ROLE_EXCEPTION = "The user does not have the right role";

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
            throw new RequiredDataException(REQUIRED_DATA_EXCEPTION);
        }

        if (!isValidPhoneNumber(restaurant.getPhoneNumber())) {
            throw new DataFormatException(WRONG_PHONE_NUMBER_EXCEPTION);
        }

        if (!isValidRestaurantName(restaurant.getName())) {
            throw new DataFormatException(ONLY_NUMBER_NAME_EXCEPTION);
        }

        String userRole = userServicePort.getUserRole(restaurant.getOwnerId());
        if (userRole == null) {
            throw new DomainException(OWNER_NOT_FOUND_EXCEPTION);
        }

        if (!Role.OWNER.equalsIgnoreCase(userRole)) {
            throw new DomainException(WRONG_ROLE_EXCEPTION);
        }

        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public boolean exist(Long id) {
        return restaurantPersistencePort.exist(id);
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