package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.exception.ObjectNotFoundException;
import com.pragma.plazoleta.domain.exception.RequiredDataException;
import com.pragma.plazoleta.domain.model.Dish;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
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

    private static final Long NULL_LONG = null;

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
                .creatorId(1L)
                .build();
    }

    @Test
    void saveDish_ShouldThrowRequiredDataException_WhenMissingSomeField() {
        // Arrange
        defaulDish.setRestaurantId(NULL_LONG);

        // Act - Assert
        assertThrows(RequiredDataException.class, () -> dishUseCase.saveDish(defaulDish));
        verify(restaurantServicePortMock, never()).exist(anyLong());
        verify(restaurantServicePortMock, never()).matchOwner(anyLong(), anyLong());
        verify(dishPersistencePortMock, never()).saveDish(any());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    void saveDish_ShouldThrowDomainException_WhenDishPriceIsWrong(Integer price) {
        // Arrange
        defaulDish.setPrice(price);

        // Act - Assert
        assertThrows(DomainException.class, () -> dishUseCase.saveDish(defaulDish));
        verify(restaurantServicePortMock, never()).exist(anyLong());
        verify(restaurantServicePortMock, never()).matchOwner(anyLong(), anyLong());
        verify(dishPersistencePortMock, never()).saveDish(any());
    }

    @Test
    void saveDish_ShouldThrowObjectNotFoundException_WhenRestaurantDoesNotExist() {
        // Arrange
        when(restaurantServicePortMock.exist(defaulDish.getRestaurantId())).thenReturn(false);

        // Act - Assert
        assertThrows(ObjectNotFoundException.class, () -> dishUseCase.saveDish(defaulDish));
        verify(restaurantServicePortMock).exist(anyLong());
        verify(restaurantServicePortMock, never()).matchOwner(anyLong(), anyLong());
        verify(dishPersistencePortMock, never()).saveDish(any());
    }

    @Test
    void saveDish_ShouldThrowDomainException_WhenRestaurantDoesNotMatchWithCreator() {
        // Arrange
        when(restaurantServicePortMock.exist(defaulDish.getRestaurantId())).thenReturn(false);
        when(restaurantServicePortMock.matchOwner(defaulDish.getRestaurantId(), defaulDish.getCreatorId()))
                .thenReturn(false);

        // Act - Assert
        assertThrows(ObjectNotFoundException.class, () -> dishUseCase.saveDish(defaulDish));
        verify(restaurantServicePortMock).exist(anyLong());
        verify(dishPersistencePortMock, never()).saveDish(any());
    }

    @Test
    void saveDish_ShouldSaveDish_WhenEverythingIsOk() {
        // Arrange
        when(restaurantServicePortMock.exist(defaulDish.getRestaurantId())).thenReturn(true);
        when(restaurantServicePortMock.matchOwner(defaulDish.getRestaurantId(), defaulDish.getCreatorId()))
                .thenReturn(true);

        // Act - Assert
        assertDoesNotThrow(() -> dishUseCase.saveDish(defaulDish));
        verify(restaurantServicePortMock).exist(anyLong());
        verify(restaurantServicePortMock).matchOwner(anyLong(), anyLong());
        verify(dishPersistencePortMock).saveDish(any());
        assertTrue(defaulDish.getActive());
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

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    void updateDish_ShouldThrowDomainException_WhenDishPriceIsWrong(Integer price) {
        // Arrange
        defaulDish.setPrice(price);

        // Act - Assert
        assertThrows(DomainException.class, () -> dishUseCase.updateDish(defaulDish));
        verify(dishPersistencePortMock, never()).getDish(anyLong());
        verify(restaurantServicePortMock, never()).matchOwner(anyLong(), anyLong());
        verify(dishPersistencePortMock, never()).saveDish(any(Dish.class));
    }

    @Test
    void updateDish_ShouldThrowDomainException_WhenRestaurantDoesNotMatchWithCreator() {
        // Arrange
        when(restaurantServicePortMock.matchOwner(defaulDish.getRestaurantId(), defaulDish.getCreatorId()))
                .thenReturn(false);

        // Act - Assert
        assertThrows(DomainException.class, () -> dishUseCase.updateDish(defaulDish));
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
                .creatorId(1L)
                .build();

        when(dishPersistencePortMock.getDish(anyLong())).thenReturn(defaulDish);
        when(restaurantServicePortMock.matchOwner(defaulDish.getRestaurantId(), dishToUpdate.getCreatorId()))
                .thenReturn(true);
        when(dishPersistencePortMock.saveDish(any(Dish.class))).thenReturn(defaulDish);

        // Act
        var updatedDish = dishUseCase.updateDish(dishToUpdate);

        // Assert
        assertNotNull(updatedDish);
        assertEquals(dishToUpdate.getPrice(), defaulDish.getPrice());
        assertEquals(dishToUpdate.getDescription(), defaulDish.getDescription());
    }

    @Test
    void changeDishStatus_ShouldThrowRequiredDataException_WhenSomeDataIsNull() {
        // Arrange
        Long dishId = 1L;
        Boolean status = true;

        // Act - Assert
        assertThrows(RequiredDataException.class, () -> dishUseCase.changeDishStatus(dishId, status, null));
        verify(dishPersistencePortMock, never()).getDish(anyLong());
        verify(restaurantServicePortMock, never()).matchOwner(anyLong(), anyLong());
        verify(dishPersistencePortMock, never()).saveDish(any(Dish.class));
    }

    @Test
    void changeDishStatus_ShouldThrowDomainException_WhenRestaurantDoesNotMatchUser() {
        // Arrange
        Long dishId = 1L;
        Boolean status = true;
        Long userId = 1L;

        when(dishPersistencePortMock.getDish(dishId)).thenReturn(defaulDish);
        when(restaurantServicePortMock.matchOwner(defaulDish.getRestaurantId(), userId)).thenReturn(false);

        // Act - Assert
        assertThrows(DomainException.class, () -> dishUseCase.changeDishStatus(dishId, status, userId));
        verify(dishPersistencePortMock).getDish(dishId);
        verify(dishPersistencePortMock, never()).saveDish(any(Dish.class));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void changeDishStatus_ShouldChangeStatus_WhenEverythingIsOk(Boolean status) {
        // Arrange
        Long dishId = 1L;
        Long userId = 1L;

        when(dishPersistencePortMock.getDish(dishId)).thenReturn(defaulDish);
        when(restaurantServicePortMock.matchOwner(defaulDish.getRestaurantId(), userId)).thenReturn(true);

        // Act - Assert
        assertDoesNotThrow(() -> dishUseCase.changeDishStatus(dishId, status, userId));
        assertEquals(status, defaulDish.getActive());
        verify(dishPersistencePortMock).getDish(dishId);
        verify(restaurantServicePortMock).matchOwner(anyLong(), anyLong());
        verify(dishPersistencePortMock).saveDish(any(Dish.class));
    }

}