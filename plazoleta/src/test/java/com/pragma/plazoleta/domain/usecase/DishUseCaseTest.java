package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.exception.ObjectNotFoundException;
import com.pragma.plazoleta.domain.exception.RequiredDataException;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DishUseCaseTest {

    private final IDishPersistencePort dishPersistencePortMock = Mockito.mock(IDishPersistencePort.class);
    private final IRestaurantServicePort restaurantServicePortMock = Mockito.mock(IRestaurantServicePort.class);

    private final DishUseCase dishUseCase = new DishUseCase(
            dishPersistencePortMock,
            restaurantServicePortMock
    );

    private Dish defaulDish;

    @BeforeEach
    void setup() {
        defaulDish = Dish.builder()
                .name("Plato al test")
                .price(98)
                .description("Un plato de pruebas")
                .urlImage("example.com")
                .category("Test category")
                .restaurantId(1L)
                .build();
    }

    @Test
    void saveDish_ShouldThrowRequiredDataException_WhenMissingSomeField() {
        // Arrange
        defaulDish.setRestaurantId(null);

        // Act - Assert
        assertThrows(RequiredDataException.class, () -> dishUseCase.saveDish(defaulDish));
        verify(restaurantServicePortMock, never()).exist(anyLong());
        verify(dishPersistencePortMock, never()).saveDish(any());
    }

    @Test
    void saveDish_ShouldThrowObjectNotFoundException_WhenRestaurantDoesNotExist() {
        // Arrange
        when(restaurantServicePortMock.exist(defaulDish.getRestaurantId())).thenReturn(false);

        // Act - Assert
        assertThrows(ObjectNotFoundException.class, () -> dishUseCase.saveDish(defaulDish));
        verify(restaurantServicePortMock).exist(anyLong());
        verify(dishPersistencePortMock, never()).saveDish(any());
    }

    @Test
    void saveDish_ShouldSaveDish_WhenEverythingIsOk() {
        // Arrange
        when(restaurantServicePortMock.exist(defaulDish.getRestaurantId())).thenReturn(true);

        // Act - Assert
        assertDoesNotThrow(() -> dishUseCase.saveDish(defaulDish));
        verify(restaurantServicePortMock).exist(anyLong());
        verify(dishPersistencePortMock).saveDish(any());
        assertTrue(defaulDish.isActive());
    }

    @Test
    void updateDish_ShouldThrowRequiredDataException_WhenRequiredDataIsNotPresent() {
        // Arrange
        defaulDish.setDescription(null);

        // Act - Assert
        assertThrows(RequiredDataException.class, () -> dishUseCase.updateDish(defaulDish));
        verify(dishPersistencePortMock, never()).getDish(anyLong());
        verify(dishPersistencePortMock, never()).saveDish(any(Dish.class));
    }

    @Test
    void updateDish_ShouldReturnUpdatedDish_WhenEverythingIsOk() {
        // Arrange
        var dishToUpdate = Dish.builder()
                .id(1L)
                .price(123)
                .description("Fixed description")
                .build();
        when(dishPersistencePortMock.getDish(anyLong())).thenReturn(defaulDish);
        when(dishPersistencePortMock.saveDish(any(Dish.class))).thenReturn(defaulDish);

        // Act
        var updatedDish = dishUseCase.updateDish(dishToUpdate);

        // Assert
        assertNotNull(updatedDish);
        assertEquals(dishToUpdate.getPrice(), defaulDish.getPrice());
        assertEquals(dishToUpdate.getDescription(), defaulDish.getDescription());
    }

}