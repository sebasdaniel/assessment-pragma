package com.pragma.plazoleta.user.domain.api;

import com.pragma.plazoleta.user.domain.model.User;

public interface IUserServicePort {

    void saveOwner(User user);

    void saveEmployee(User user);

    void saveClient(User user);

    User getUser(Long id);
}