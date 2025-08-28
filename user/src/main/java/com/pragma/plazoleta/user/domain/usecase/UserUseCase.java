package com.pragma.plazoleta.user.domain.usecase;

import com.pragma.plazoleta.user.domain.api.IUserServicePort;
import com.pragma.plazoleta.user.domain.exception.InvalidFormatException;
import com.pragma.plazoleta.user.domain.exception.MissingDataException;
import com.pragma.plazoleta.user.domain.model.User;
import com.pragma.plazoleta.user.domain.spi.IPasswordEncoderPort;
import com.pragma.plazoleta.user.domain.spi.IUserPersistencePort;
import java.util.List;

public class UserUseCase implements IUserServicePort {

    private static final String OWNER_ROLE = "propietario";

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;

    public UserUseCase(
            IUserPersistencePort userPersistencePort,
            IPasswordEncoderPort passwordEncoderPort
    ) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public void saveOwner(User user) {
        validateOwnerUser(user);

        var encodedPassword = passwordEncoderPort.encode(user.getPassword());

        user.setRole(OWNER_ROLE);
        user.setPassword(encodedPassword);

        userPersistencePort.saveUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userPersistencePort.getAllUsers();
    }

    private void validateOwnerUser(User user) {
        if (!haveOwnerRequiredData(user)) {
            throw new MissingDataException("One or more required fields are missing");
        }

        if (!user.isValidEmail()) {
            throw new InvalidFormatException("Invalid email format");
        }

        if (!user.isValidPhoneNumber()) {
            throw new InvalidFormatException("Invalid phone number format");
        }

        if (!user.hasLegalAge()) {
            throw new InvalidFormatException("User age must be 18 or more");
        }
    }

    private boolean haveOwnerRequiredData(User user) {
        return user.getName() != null && user.getLastName() != null && user.getIdNumber() != null
                && user.getPhoneNumber() != null && user.getBirthdate() != null
                && user.getEmail() != null && user.getPassword() != null;
    }
}