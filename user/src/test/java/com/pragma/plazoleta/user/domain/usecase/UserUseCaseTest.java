package com.pragma.plazoleta.user.domain.usecase;


import com.pragma.plazoleta.user.domain.exception.DomainException;
import com.pragma.plazoleta.user.domain.model.User;
import com.pragma.plazoleta.user.domain.spi.IBcryptEncoderPort;
import com.pragma.plazoleta.user.domain.spi.IUserPersistencePort;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserUseCaseTest {

    private final IUserPersistencePort userPersistencePortMock = Mockito.mock(IUserPersistencePort.class);
    private final IBcryptEncoderPort bcryptEncoderPortMock = Mockito.mock(IBcryptEncoderPort.class);

    private final UserUseCase userUseCase = new UserUseCase(userPersistencePortMock, bcryptEncoderPortMock);

    private User defaultUser;

    @BeforeEach
    void setup() {
        LocalDate birthdate = LocalDate.of(2001, 2, 1);
        defaultUser = new User(
                null,
                "Pepito",
                "Perez",
                1067123456,
                "+573011234567",
                birthdate,
                "example@example.com",
                "password123",
                null
        );
    }

    @Test
    void saveOwner_ShouldThrowException_WhenRequiredDataIsNotPresent() {
        // Arrange
        defaultUser.setPassword(null);

        // Act - Assert
        assertThrows(DomainException.class, () -> userUseCase.saveOwner(defaultUser));
    }

    @Test
    void saveOwner_ShouldThrowException_WhenEmailIsNotValid() {
        // Arrange
        defaultUser.setEmail("testemail.com");

        // Act - Assert
        assertThrows(DomainException.class, () -> userUseCase.saveOwner(defaultUser));
    }

    @Test
    void saveOwner_ShouldThrowException_WhenPhoneNumberIsNotValid() {
        // Arrange
        defaultUser.setPhoneNumber("+57 (301) 12345567");

        // Act - Assert
        assertThrows(DomainException.class, () -> userUseCase.saveOwner(defaultUser));
    }

    @Test
    void saveOwner_ShouldThrowException_WhenUserDoesNotHaveLegalAge() {
        // Arrange
        var notLegalAge = LocalDate.now().minusYears(10);
        defaultUser.setBirthdate(notLegalAge);

        // Act - Assert
        assertThrows(DomainException.class, () -> userUseCase.saveOwner(defaultUser));
    }

    @Test
    void saveOwner_ShouldSaveUser_WhenEverythingIsOk() {
        // Arrange
        var encodedPassword = "3nc0d3dp4ssw0rd";

        when(bcryptEncoderPortMock.encode(anyString())).thenReturn(encodedPassword);

        // Act - Assert
        assertDoesNotThrow(() -> userUseCase.saveOwner(defaultUser));
        assertEquals("propietario", defaultUser.getRole());
        assertEquals(encodedPassword, defaultUser.getPassword());

        verify(bcryptEncoderPortMock).encode(anyString());
    }

}