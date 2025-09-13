package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.exception.DataFormatException;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.exception.RequiredDataException;
import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta.domain.spi.IUserServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantUseCaseTest {

    private final IRestaurantPersistencePort restaurantPersistentPortMock =
            Mockito.mock(IRestaurantPersistencePort.class);
    private final IUserServicePort userServicePortMock = Mockito.mock(IUserServicePort.class);

    private final RestaurantUseCase restaurantUseCase = new RestaurantUseCase(
            restaurantPersistentPortMock,
            userServicePortMock
    );

    private Restaurant defaultRestaurant;

    @BeforeEach
    void setup() {
        defaultRestaurant = new Restaurant(
                null,
                111222333,
                "Test Restaurant 2",
                "Av 1 #2-3",
                "+571234567890",
                "http://example.com",
                1L
        );
    }

    @Test
    void saveRestaurant_ShouldThrowRequiredDataException_WhenDataMissing() {
        // Arrange
        defaultRestaurant.setOwnerId(null);

        // Act -Assert
        assertThrows(RequiredDataException.class, () -> restaurantUseCase.saveRestaurant(defaultRestaurant));
        verify(userServicePortMock, never()).getUserRole(anyLong());
        verify(restaurantPersistentPortMock, never()).saveRestaurant(any(Restaurant.class));
    }

    @Test
    void saveRestaurant_ShouldThrowDataFormatException_WhenPhoneNumberIsWrong() {
        // Arrange
        defaultRestaurant.setPhoneNumber("(301) 123 4567");

        // Act -Assert
        assertThrows(DataFormatException.class, () -> restaurantUseCase.saveRestaurant(defaultRestaurant));
        verify(userServicePortMock, never()).getUserRole(anyLong());
        verify(restaurantPersistentPortMock, never()).saveRestaurant(any(Restaurant.class));
    }

    @Test
    void saveRestaurant_ShouldThrowDataFormatException_WhenRestaurantNameIsOnlyNumber() {
        // Arrange
        defaultRestaurant.setName("123");

        // Act -Assert
        assertThrows(DataFormatException.class, () -> restaurantUseCase.saveRestaurant(defaultRestaurant));
        verify(userServicePortMock, never()).getUserRole(anyLong());
        verify(restaurantPersistentPortMock, never()).saveRestaurant(any(Restaurant.class));
    }

    @Test
    void saveRestaurant_ShouldThrowDomainException_WhenUserDoesNotExist() {
        // Arrange
        when(userServicePortMock.getUserRole(defaultRestaurant.getOwnerId())).thenReturn(null);

        // Act -Assert
        assertThrows(DomainException.class, () -> restaurantUseCase.saveRestaurant(defaultRestaurant));
        verify(userServicePortMock).getUserRole(defaultRestaurant.getOwnerId());
        verify(restaurantPersistentPortMock, never()).saveRestaurant(any(Restaurant.class));
    }

    @Test
    void saveRestaurant_ShouldThrowDomainException_WhenUserDoesNotHaveOwnerRole() {
        // Arrange
        when(userServicePortMock.getUserRole(defaultRestaurant.getOwnerId())).thenReturn("other");

        // Act -Assert
        assertThrows(DomainException.class, () -> restaurantUseCase.saveRestaurant(defaultRestaurant));
        verify(userServicePortMock).getUserRole(defaultRestaurant.getOwnerId());
        verify(restaurantPersistentPortMock, never()).saveRestaurant(any(Restaurant.class));
    }

    @Test
    void saveRestaurant_ShouldSaveRestaurant_WhenEverythingIsOk() {
        // Arrange
        when(userServicePortMock.getUserRole(defaultRestaurant.getOwnerId())).thenReturn(Role.OWNER);

        // Act -Assert
        assertDoesNotThrow(() -> restaurantUseCase.saveRestaurant(defaultRestaurant));
        verify(userServicePortMock).getUserRole(defaultRestaurant.getOwnerId());
        verify(restaurantPersistentPortMock).saveRestaurant(defaultRestaurant);
    }
}