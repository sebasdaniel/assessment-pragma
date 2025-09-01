package com.pragma.plazoleta.user.domain.spi;

import com.pragma.plazoleta.user.domain.model.User;

public interface IUserPersistencePort {
    User saveUser(User user);
    User getUser(Long id);
}