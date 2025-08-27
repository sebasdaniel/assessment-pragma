package com.pragma.plazoleta.user.domain.usecase;

import com.pragma.plazoleta.user.domain.api.IUserServicePort;
import com.pragma.plazoleta.user.domain.exception.DomainException;
import com.pragma.plazoleta.user.domain.model.User;
import com.pragma.plazoleta.user.domain.spi.IUserPersistencePort;

import java.util.List;

public class UserUseCase implements IUserServicePort {

    private static final String OWNER_ROLE = "propietario";

    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void saveOwner(User user) {
        validateOwnerUser(user);

        user.setRole(OWNER_ROLE);

        userPersistencePort.saveUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userPersistencePort.getAllUsers();
    }

    private void validateOwnerUser(User user) {
        if (!haveOwnerRequiredData(user)) {
            throw new DomainException("One or more required fields are missing");
        }

        if (!user.isValidEmail()) {
            throw new DomainException("Invalid email format");
        }

        if (!user.isValidPhoneNumber()) {
            throw new DomainException("Invalid phone number format");
        }

        if (!user.hasLegalAge()) {
            throw new DomainException("User age must be 18 or more");
        }
    }

    private boolean haveOwnerRequiredData(User user) {
        return user.getName() != null && user.getLastName() != null && user.getIdNumber() != null
                && user.getPhoneNumber() != null && user.getBirthdate() != null
                && user.getEmail() != null && user.getPassword() != null;
    }
}