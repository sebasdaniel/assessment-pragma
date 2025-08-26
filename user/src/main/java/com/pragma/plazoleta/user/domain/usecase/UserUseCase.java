package com.pragma.plazoleta.user.domain.usecase;

import com.pragma.plazoleta.user.domain.api.IUserServicePort;
import com.pragma.plazoleta.user.domain.model.User;
import com.pragma.plazoleta.user.domain.spi.IUserPersistencePort;

import java.util.List;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void saveOwner(User user) {
        if (user.getName().isEmpty()) {
            //TODO: lanzar exception if the user dont have the right fields
        }
        userPersistencePort.saveUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userPersistencePort.getAllUsers();
    }
}