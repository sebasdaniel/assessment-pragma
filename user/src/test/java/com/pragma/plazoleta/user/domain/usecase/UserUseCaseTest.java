package com.pragma.plazoleta.user.domain.usecase;

import com.pragma.plazoleta.user.domain.exception.InvalidFormatException;
import com.pragma.plazoleta.user.domain.exception.MissingDataException;
import com.pragma.plazoleta.user.domain.model.User;
import com.pragma.plazoleta.user.domain.model.Role;
import com.pragma.plazoleta.user.domain.spi.IPasswordEncoderPort;
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
    private final IPasswordEncoderPort bcryptEncoderPortMock = Mockito.mock(IPasswordEncoderPort.class);

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

    /**
     * #######################################
     * ##    Tests for saveOwner method     ##
     * #######################################
     */
    @Test
    void saveOwner_ShouldThrowException_WhenRequiredDataIsNotPresent() {
        // Arrange
        defaultUser.setBirthdate(null);

        // Act - Assert
        assertThrows(MissingDataException.class, () -> userUseCase.saveOwner(defaultUser));
    }

    @Test
    void saveOwner_ShouldThrowException_WhenEmailIsNotValid() {
        // Arrange
        defaultUser.setEmail("testemail.com");

        // Act - Assert
        assertThrows(InvalidFormatException.class, () -> userUseCase.saveOwner(defaultUser));
    }

    @Test
    void saveOwner_ShouldThrowException_WhenPhoneNumberIsNotValid() {
        // Arrange
        defaultUser.setPhoneNumber("+57 (301) 12345567");

        // Act - Assert
        assertThrows(InvalidFormatException.class, () -> userUseCase.saveOwner(defaultUser));
    }

    @Test
    void saveOwner_ShouldThrowException_WhenUserDoesNotHaveLegalAge() {
        // Arrange
        LocalDate notLegalAge = LocalDate.now().minusYears(10);
        defaultUser.setBirthdate(notLegalAge);

        // Act - Assert
        assertThrows(InvalidFormatException.class, () -> userUseCase.saveOwner(defaultUser));
    }

    @Test
    void saveOwner_ShouldSaveUser_WhenEverythingIsOk() {
        // Arrange
        String encodedPassword = "3nc0d3dp4ssw0rd";

        when(bcryptEncoderPortMock.encode(anyString())).thenReturn(encodedPassword);

        // Act - Assert
        assertDoesNotThrow(() -> userUseCase.saveOwner(defaultUser));
        assertEquals(Role.OWNER, defaultUser.getRole());
        assertEquals(encodedPassword, defaultUser.getPassword());

        verify(bcryptEncoderPortMock).encode(anyString());
    }

    /**
     * #######################################
     * ##    Tests for saveEmployee method  ##
     * #######################################
     */
    @Test
    void saveEmployee_ShouldThrowException_WhenRequiredDataIsNotPresent() {
        // Arrange
        defaultUser.setPassword(null);

        // Act - Assert
        assertThrows(MissingDataException.class, () -> userUseCase.saveEmployee(defaultUser));
    }

    @Test
    void saveEmployee_ShouldThrowException_WhenEmailIsNotValid() {
        // Arrange
        defaultUser.setEmail("wrongemail.com");

        // Act - Assert
        assertThrows(InvalidFormatException.class, () -> userUseCase.saveEmployee(defaultUser));
    }

    @Test
    void saveEmployee_ShouldThrowException_WhenPhoneNumberIsNotValid() {
        // Arrange
        defaultUser.setPhoneNumber("+57 (301) 12345567");

        // Act - Assert
        assertThrows(InvalidFormatException.class, () -> userUseCase.saveEmployee(defaultUser));
    }

    @Test
    void saveEmployee_ShouldSaveUser_WhenEverythingIsOk() {
        // Arrange
        String encodedPassword = "3nc0d3dp4ssw0rd";

        when(bcryptEncoderPortMock.encode(anyString())).thenReturn(encodedPassword);

        // Act - Assert
        assertDoesNotThrow(() -> userUseCase.saveEmployee(defaultUser));
        assertEquals(Role.EMPLOYEE, defaultUser.getRole());
        assertEquals(encodedPassword, defaultUser.getPassword());

        verify(bcryptEncoderPortMock).encode(anyString());
    }

    /**
     * #######################################
     * ##    Tests for saveClient method    ##
     * #######################################
     */
    @Test
    void saveClient_ShouldThrowException_WhenRequiredDataIsNotPresent() {
        // Arrange
        defaultUser.setPassword(null);

        // Act - Assert
        assertThrows(MissingDataException.class, () -> userUseCase.saveClient(defaultUser));
    }

    @Test
    void saveClient_ShouldThrowException_WhenEmailIsNotValid() {
        // Arrange
        defaultUser.setEmail("wrongemail.com");

        // Act - Assert
        assertThrows(InvalidFormatException.class, () -> userUseCase.saveClient(defaultUser));
    }

    @Test
    void saveClient_ShouldThrowException_WhenPhoneNumberIsNotValid() {
        // Arrange
        defaultUser.setPhoneNumber("+57 301-123-45567");

        // Act - Assert
        assertThrows(InvalidFormatException.class, () -> userUseCase.saveClient(defaultUser));
    }

    @Test
    void saveClient_ShouldSaveUser_WhenEverythingIsOk() {
        // Arrange
        String encodedPassword = "3nc0d3dp4ssw0rd";

        when(bcryptEncoderPortMock.encode(anyString())).thenReturn(encodedPassword);

        // Act - Assert
        assertDoesNotThrow(() -> userUseCase.saveClient(defaultUser));
        assertEquals(Role.CLIENT, defaultUser.getRole());
        assertEquals(encodedPassword, defaultUser.getPassword());

        verify(bcryptEncoderPortMock).encode(anyString());
    }

}