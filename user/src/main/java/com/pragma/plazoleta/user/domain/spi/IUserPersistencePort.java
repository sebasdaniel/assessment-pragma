package com.pragma.plazoleta.user.domain.spi;

import com.pragma.plazoleta.user.domain.model.User;

import java.util.List;

public interface IUserPersistencePort {
    User saveUser(User user);

    List<User> getAllUsers();
}