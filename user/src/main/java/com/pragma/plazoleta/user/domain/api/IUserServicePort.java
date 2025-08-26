package com.pragma.plazoleta.user.domain.api;

import com.pragma.plazoleta.user.domain.model.User;

import java.util.List;

public interface IUserServicePort {

    void saveOwner(User user);

    List<User> getAllUsers();
}